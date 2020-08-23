package runner.controller;

import cards.UserCardOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Основной контроллер, отвечающий за операции с картами
 */
@RestController
@RequestMapping("operations")
public class OperationsController {
    UserCardOperations operations = new UserCardOperations();

    @RequestMapping(value = "{cardId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Map<String, String>> getCardBalance(@PathVariable int cardId) {
        return new ResponseEntity<>(operations.getBalance(cardId), HttpStatus.OK);
    }

    @RequestMapping(value = "withdraw", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Map<String, String>> withdrawFunds(@RequestBody Map<String, String> requestBody) {
        return new ResponseEntity<>(
                operations.withdrawFunds(Long.parseLong(requestBody.get("cardId")), Long.parseLong(requestBody.get("funds"))), HttpStatus.OK);
    }

    @RequestMapping(value = "add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Map<String, String>> addFunds(@RequestBody Map<String, String> requestBody) {
        return new ResponseEntity<>(
                operations.addFunds(Long.parseLong(requestBody.get("cardId")), Long.parseLong(requestBody.get("funds"))), HttpStatus.OK);
    }

    @RequestMapping(value = "transfer", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Map<String, String>> transferFunds(@RequestBody Map<String, String> requestBody) {
        return new ResponseEntity<>(
                operations.transferFunds(Long.parseLong(requestBody.get("cardIdFrom")),
                        Long.parseLong(requestBody.get("cardIdTo")),
                        Long.parseLong(requestBody.get("funds"))), HttpStatus.OK);
    }
}
