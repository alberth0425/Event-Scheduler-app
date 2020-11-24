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

    public Agreement getAgreementByUserName(String username) throws AgreementDoesNotExistException {
        //check that the agreement with given username exists
        if (!allAgreements.containsKey(username)){
            throw new AgreementDoesNotExistException();
        }
        return allAgreements.get(username);
    }

    public void signAgreement(String username, String firstName, String lastName) throws AgreementAlreadyExistException {
        // Check double signing exceptions
        if (allAgreements.containsKey(username)){
            throw new AgreementAlreadyExistException();
        }

        Agreement agreement = new Agreement(username, firstName, lastName);
        allAgreements.put(username, agreement);

    }

    public static class AgreementException extends Exception {}
    public static class AgreementDoesNotExistException extends AgreementException {}
    public static class AgreementAlreadyExistException extends AgreementException{}
}
