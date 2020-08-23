package cards;

import files.ProjectFileWorker;

import java.util.HashMap;

/**
 * Банковские операции с картами
 */
public class UserCardOperations {
    private static CardArray userCards = new CardArray();

    public UserCardOperations() {
        UserCardOperations.userCards = ProjectFileWorker.getInstance().readCards();
    }

    /**
     * Зачисление средств на карту
     *
     * @param cardId             id счета на который зачисляются средства
     * @param amountOfMoneyToAdd сумма средств для начисления
     * @return результат операции в виде HashMap
     */
    public synchronized HashMap<String, String> addFunds(long cardId, long amountOfMoneyToAdd) {
        ResultOperation resultOperation;
        UserCard card = userCards.getCardById(cardId);
        if (card.equals(UserCard.EMPTY_CARD)) {
            resultOperation = ResultOperation.CARD_NOT_FOUND;
        } else {
            card.setFunds(card.getFunds() + amountOfMoneyToAdd);
            ProjectFileWorker.getInstance().writeCards(userCards);
            resultOperation = ResultOperation.SUCCESS;
        }
        return new HashMap<String, String>() {{
            put("result", resultOperation.getOperationResult());
        }};
    }

    /**
     * Снятие средств со счета
     *
     * @param cardId                 id счета с которого списываются средства
     * @param amountOfMoneyToGiveOut сумма средств для перевода
     * @return результат операции в виде ResultOperation
     */
    public synchronized HashMap<String, String> withdrawFunds(long cardId, long amountOfMoneyToGiveOut) {
        ResultOperation resultOperation;
        UserCard card = userCards.getCardById(cardId);
        if (card.equals(UserCard.EMPTY_CARD)) {
            resultOperation = ResultOperation.CARD_NOT_FOUND;
        } else if (card.getFunds() - amountOfMoneyToGiveOut < 0) {
            resultOperation = ResultOperation.INSUFFICIENT_FUNDS;
        } else {
            card.setFunds(card.getFunds() - amountOfMoneyToGiveOut);
            ProjectFileWorker.getInstance().writeCards(userCards);
            resultOperation = ResultOperation.SUCCESS;
        }
        return new HashMap<String, String>() {{
            put("result", resultOperation.getOperationResult());
        }};
    }

    /**
     * Получить баланс карты
     *
     * @param cardId id счета с которого запрашивается баланс
     * @return результат операции в виде ResultOperation
     */
    public synchronized HashMap<String, String> getBalance(long cardId) {
        ResultOperation resultOperation;
        HashMap<String, String> response = new HashMap<>();
        long funds = 0;
        UserCard card = userCards.getCardById(cardId);
        if (card.equals(UserCard.EMPTY_CARD)) {
            resultOperation = ResultOperation.CARD_NOT_FOUND;
        } else {
            funds = card.getFunds();
            response.put("cardBalance", String.valueOf(funds));
            resultOperation = ResultOperation.SUCCESS;
        }
        response.put("result", resultOperation.getOperationResult());
        return response;
    }

    /**
     * Перевод средств на другой счет ID
     *
     * @param cardId          id счета с которого списываются средства
     * @param anotherCardId   id счета на который зачисляются средства
     * @param fundsToTransfer сумма средств для перевода
     * @return результат операции в виде ResultOperation
     */
    public synchronized HashMap<String, String> transferFunds(long cardId, long anotherCardId, long fundsToTransfer) {
        ResultOperation resultOperation;
        UserCard card = userCards.getCardById(cardId);
        UserCard anotherCard = userCards.getCardById(anotherCardId);
        if (card.equals(UserCard.EMPTY_CARD) || anotherCard.equals(UserCard.EMPTY_CARD)) {
            resultOperation = ResultOperation.CARD_NOT_FOUND;
        } else if (card.equals(anotherCard)) {
            resultOperation = ResultOperation.NOT_SUPPORTED_OPERATION;
        } else if (card.getFunds() - fundsToTransfer < 0) {
            resultOperation = ResultOperation.INSUFFICIENT_FUNDS;
        } else {
            card.setFunds(card.getFunds() - fundsToTransfer);
            anotherCard.setFunds(anotherCard.getFunds() + fundsToTransfer);
            ProjectFileWorker.getInstance().writeCards(userCards);
            resultOperation = ResultOperation.SUCCESS;
        }
        return new HashMap<String, String>() {{
            put("result", resultOperation.getOperationResult());
        }};
    }

    public CardArray getUserCards() {
        return UserCardOperations.userCards;
    }
}
