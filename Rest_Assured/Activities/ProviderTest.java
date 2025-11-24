package examples;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

@Provider("UserProvider")
@PactFolder("target/pacts")
public class ProviderTest {
    @BeforeEach
    public void setUp(PactVerificationContext context) {
        HttpTestTarget target = new HttpTestTarget("localhost", 8585);
        context.setTarget(target);
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    public void providerTest(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @State("POST Request")
    public void state1() {
    }

//    @State("GET Request")
//    public void state2() {
//    }
//
//    @State("DELETE Request")
//    public void state3() {
//    }

//    @State("GET ALL Request")
//    public void state4() {
//    }
}