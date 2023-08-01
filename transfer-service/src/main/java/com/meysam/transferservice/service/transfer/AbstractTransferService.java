package com.meysam.transferservice.service.transfer;


import com.meysam.transferservice.enums.TransferStatus;
import com.meysam.transferservice.model.entity.Card;
import com.meysam.transferservice.model.entity.Transfer;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractTransferService {


    private ConcurrentHashMap<String, Long> concurrentTransfersWithdrawAmount = new ConcurrentHashMap<>();


    /**
     * <p>Begins a transaction for transferring money
     * from Card_1 to Card_2 in {@link Transfer} object
     * and sends successful transaction's notification to
     * RabbitMQ queue in another transaction.</p>
     *
     * @param transfer of type {@link Transfer}
     * @return Mono of type {@link ResponseEntity}
     */
    public abstract Mono<ResponseEntity<String>> transfer(Transfer transfer);


    /**
     * <p>Checks Bank Service Provider's response for returning
     * a proper {@link TransferStatus}.  </p>
     */
    public Mono<TransferStatus> checkExchangeSPResponse(Mono<Boolean> response) {

        return response.map(res -> {
            if (res) {

                // transaction successful:
                return TransferStatus.SUCCESSFUL;

            } else {
                // transaction failed:
                return TransferStatus.FAILED;
            }
        }).onErrorResume(e -> {
            //transaction uncommitted:
            return Mono.just(TransferStatus.UNCOMMITED);
        });
    }

    /**
     * Checks first card for enough balance.
     *
     * @param transfer object of {@link Transfer}
     * @return Mono of type boolean
     */
    public abstract Mono<Boolean> checkFirstCardBalance(Transfer transfer);


    /**
     * Calls a Bank Service Provider.
     * <p>each transfer provider implementation should call a provider
     * based on its own logic.</p>
     *
     * @param URI      the API Address of Provider.
     * @param transfer the {@link Transfer} object.
     * @return Mono of type boolean
     */
    public abstract Mono<Boolean> callExchangeSP(String URI, Transfer transfer);


    /**
     * Just sets a {@link TransferStatus} to {@link Transfer} object.
     */
    public Mono<Transfer> updateTransferStatus(Transfer transfer, TransferStatus status) {
        return Mono.just(transfer)
                .map(updated_transfer -> {
                    updated_transfer.setStatus(status);
                    return updated_transfer;
                });
    }


    /**
     * Updates cards balance after successful transaction
     */
    public abstract Mono<Boolean> updateCardsBalances(Transfer transfer);


    /**
     * Updates a {@link Card} object optimistically
     */
    public abstract Mono<Boolean> updateOptimistic(Card card, Long newBalance);

    /**
     * Notifies the Notification Service by sending
     * successful transfer's data to RabbitMQ queue.
     */
    public abstract void sendMessage(String message);

    /**
     * In the case of concurrent transfers on a card, checks total amount
     * of all online transfers that are going to withdraw from that card
     */
    public Mono<Boolean> CanBeAddedToConcurrentWithdrawals(String cardNumber, Long balance, Long amount) {

        if (concurrentTransfersWithdrawAmount.containsKey(cardNumber)) {

            if (balance >= amount + concurrentTransfersWithdrawAmount.get(cardNumber)) {
                concurrentTransfersWithdrawAmount.put(cardNumber, concurrentTransfersWithdrawAmount.get(cardNumber) + amount);
                return Mono.just(true);
            }

        } else {
            if (balance >= amount)
                return Mono.just(true);
            else return Mono.just(false);
        }

        return Mono.just(false);
    }

    /**
     * Subtracts a completed transfer's amount from total amount of concurrent
     * transfers on a card, and removes all related data if total amount became zero
     */
    public void removeFromOnlineWithdrawals(String cardNumber, Long amount) {
        if (concurrentTransfersWithdrawAmount.containsKey(cardNumber)) {
            concurrentTransfersWithdrawAmount.put(cardNumber, concurrentTransfersWithdrawAmount.get(cardNumber) - amount);
            if (concurrentTransfersWithdrawAmount.get(cardNumber) == 0L)
                concurrentTransfersWithdrawAmount.remove(cardNumber);
        }
    }
}
