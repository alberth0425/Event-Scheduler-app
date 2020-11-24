package use_cases;

import entities.Agreement;
import entities.Event;
import entities.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AgreementService {
    private HashMap<String, Agreement> allAgreements = new HashMap<>();

    /**
     * singleton implementation.
     */
    public static AgreementService shared = new AgreementService();
    private AgreementService() {}

    public Agreement getAgreementByUserName(String username) throws AgreementException {
        //check that the agreement with given username exists
        if (!allAgreements.containsKey(username)){
            throw new AgreementDoesNotExistException();
        }
        return allAgreements.get(username);
    }

    public static class AgreementException extends Exception {}
    public static class AgreementDoesNotExistException extends AgreementService.AgreementException {}
}
