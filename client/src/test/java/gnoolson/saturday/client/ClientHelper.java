package gnoolson.saturday.client;

import gnoolson.saturday.client.model.entity.Client;
import gnoolson.saturday.client.model.vo.ClientAuth;
import gnoolson.saturday.client.model.vo.ConnectionError;
import gnoolson.saturday.client.model.vo.ServerURI;
import gnoolson.saturday.client.port.inbound.CreateClientUseCase;
import gnoolson.saturday.client.port.inbound.ImportClientsUseCase;
import gnoolson.saturday.client.port.inbound.UpdateClientUseCase;
import gnoolson.saturday.common.model.vo.*;
import gnoolson.saturday.common.time.vo.TimeInMs;

import java.util.Optional;

public class ClientHelper {

    public static final ProjectId PROJECT_ID_1 = ProjectId.random();
    public static final ProjectId PROJECT_ID_2 = ProjectId.random();
    public static final ProjectId PROJECT_ID_3 = ProjectId.random();

    public static final ClientId CLIENT_ID_1_1 = ClientId.random();
    public static final ClientId CLIENT_ID_2_1 = ClientId.random();
    public static final ClientId CLIENT_ID_3_1 = ClientId.random();
    public static final ClientId CLIENT_ID_4_1 = ClientId.random();

    public static final ClientId CLIENT_ID_1_2 = ClientId.random();
    public static final ClientId CLIENT_ID_2_2 = ClientId.random();
    public static final ClientId CLIENT_ID_3_2 = ClientId.random();
    public static final ClientId CLIENT_ID_4_2 = ClientId.random();

    public static final ClientId CLIENT_ID_1_3 = ClientId.random();
    public static final ClientId CLIENT_ID_2_3 = ClientId.random();
    public static final ClientId CLIENT_ID_3_3 = ClientId.random();
    public static final ClientId CLIENT_ID_4_3 = ClientId.random();

    public static final ClientName CLIENT_NAME_1_1 = ClientName.random();
    public static final ClientName CLIENT_NAME_2_1 = ClientName.random();
    public static final ClientName CLIENT_NAME_3_1 = ClientName.random();
    public static final ClientName CLIENT_NAME_4_1 = ClientName.random();

    public static final ClientName CLIENT_NAME_1_2 = ClientName.random();
    public static final ClientName CLIENT_NAME_2_2 = ClientName.random();
    public static final ClientName CLIENT_NAME_3_2 = ClientName.random();
    public static final ClientName CLIENT_NAME_4_2 = ClientName.random();

    public static final ClientName CLIENT_NAME_1_3 = ClientName.random();
    public static final ClientName CLIENT_NAME_2_3 = ClientName.random();
    public static final ClientName CLIENT_NAME_3_3 = ClientName.random();
    public static final ClientName CLIENT_NAME_4_3 = ClientName.random();

    /*
    *
    *
    * */
    public static Client createClient1_p1(){
        return new Client(
                CLIENT_ID_1_1,
                PROJECT_ID_1,
                CLIENT_NAME_1_1,
                Description.empty(),
                true,
                ServerURI.of("tcp://localhost:1883"),
                ClientAuth.of(false, AuthUsername.empty(), AuthPassword.empty()),
                TimeInMs.zero(),
                TimeInMs.zero(),
                ConnectionError.empty(),
                ScriptId.empty(),
                ScriptId.empty()
        );
    }

    public static Client createClient2_p1(){
        return new Client(
                CLIENT_ID_2_1,
                PROJECT_ID_1,
                CLIENT_NAME_2_1,
                Description.empty(),
                true,
                ServerURI.of("tcp://localhost:1883"),
                ClientAuth.of(false, AuthUsername.empty(), AuthPassword.empty()),
                TimeInMs.zero(),
                TimeInMs.zero(),
                ConnectionError.empty(),
                ScriptId.empty(),
                ScriptId.empty()
        );
    }

    public static Client createClient3_p1(){
        return new Client(
                CLIENT_ID_3_1,
                PROJECT_ID_1,
                CLIENT_NAME_3_1,
                Description.empty(),
                true,
                ServerURI.of("tcp://localhost:1883"),
                ClientAuth.of(false, AuthUsername.empty(), AuthPassword.empty()),
                TimeInMs.zero(),
                TimeInMs.zero(),
                ConnectionError.empty(),
                ScriptId.empty(),
                ScriptId.empty()
        );
    }

    public static Client createClient4_p1(){
        return new Client(
                CLIENT_ID_4_1,
                PROJECT_ID_1,
                CLIENT_NAME_4_1,
                Description.empty(),
                true,
                ServerURI.of("tcp://localhost:1883"),
                ClientAuth.of(false, AuthUsername.empty(), AuthPassword.empty()),
                TimeInMs.zero(),
                TimeInMs.zero(),
                ConnectionError.empty(),
                ScriptId.empty(),
                ScriptId.empty()
        );
    }



    public static Client createClient1_p2(){
        return new Client(
                CLIENT_ID_1_2,
                PROJECT_ID_2,
                CLIENT_NAME_1_2,
                Description.empty(),
                true,
                ServerURI.of("tcp://localhost:1883"),
                ClientAuth.of(false, AuthUsername.empty(), AuthPassword.empty()),
                TimeInMs.zero(),
                TimeInMs.zero(),
                ConnectionError.empty(),
                ScriptId.empty(),
                ScriptId.empty()
        );
    }

    public static Client createClient2_p2(){
        return new Client(
                CLIENT_ID_2_2,
                PROJECT_ID_2,
                CLIENT_NAME_2_2,
                Description.empty(),
                true,
                ServerURI.of("tcp://localhost:1883"),
                ClientAuth.of(false, AuthUsername.empty(), AuthPassword.empty()),
                TimeInMs.zero(),
                TimeInMs.zero(),
                ConnectionError.empty(),
                ScriptId.empty(),
                ScriptId.empty()
        );
    }

    public static Client createClient3_p2(){
        return new Client(
                CLIENT_ID_3_2,
                PROJECT_ID_2,
                CLIENT_NAME_3_2,
                Description.empty(),
                true,
                ServerURI.of("tcp://localhost:1883"),
                ClientAuth.of(false, AuthUsername.empty(), AuthPassword.empty()),
                TimeInMs.zero(),
                TimeInMs.zero(),
                ConnectionError.empty(),
                ScriptId.empty(),
                ScriptId.empty()
        );
    }

    public static Client createClient4_p2(){
        return new Client(
                CLIENT_ID_4_2,
                PROJECT_ID_2,
                CLIENT_NAME_4_2,
                Description.empty(),
                true,
                ServerURI.of("tcp://localhost:1883"),
                ClientAuth.of(false, AuthUsername.empty(), AuthPassword.empty()),
                TimeInMs.zero(),
                TimeInMs.zero(),
                ConnectionError.empty(),
                ScriptId.empty(),
                ScriptId.empty()
        );
    }




    public static Client createClient1_p3(){
        return new Client(
                CLIENT_ID_1_3,
                PROJECT_ID_3,
                CLIENT_NAME_1_3,
                Description.empty(),
                true,
                ServerURI.of("tcp://localhost:1883"),
                ClientAuth.of(false, AuthUsername.empty(), AuthPassword.empty()),
                TimeInMs.zero(),
                TimeInMs.zero(),
                ConnectionError.empty(),
                ScriptId.empty(),
                ScriptId.empty()
        );
    }

    public static Client createClient2_p3(){
        return new Client(
                CLIENT_ID_2_3,
                PROJECT_ID_3,
                CLIENT_NAME_2_3,
                Description.empty(),
                true,
                ServerURI.of("tcp://localhost:1883"),
                ClientAuth.of(false, AuthUsername.empty(), AuthPassword.empty()),
                TimeInMs.zero(),
                TimeInMs.zero(),
                ConnectionError.empty(),
                ScriptId.empty(),
                ScriptId.empty()
        );
    }

    public static Client createClient3_p3(){
        return new Client(
                CLIENT_ID_3_3,
                PROJECT_ID_3,
                CLIENT_NAME_3_3,
                Description.empty(),
                true,
                ServerURI.of("tcp://localhost:1883"),
                ClientAuth.of(false, AuthUsername.empty(), AuthPassword.empty()),
                TimeInMs.zero(),
                TimeInMs.zero(),
                ConnectionError.empty(),
                ScriptId.empty(),
                ScriptId.empty()
        );
    }

    public static Client createClient4_p3(){
        return new Client(
                CLIENT_ID_4_3,
                PROJECT_ID_3,
                CLIENT_NAME_4_3,
                Description.empty(),
                true,
                ServerURI.of("tcp://localhost:1883"),
                ClientAuth.of(false, AuthUsername.empty(), AuthPassword.empty()),
                TimeInMs.zero(),
                TimeInMs.zero(),
                ConnectionError.empty(),
                ScriptId.empty(),
                ScriptId.empty()
        );
    }




    public static Optional<Client> createDisabledClient(ClientId clientId) {
        return Optional.of(new Client(
                clientId,
                PROJECT_ID_1,
                CLIENT_NAME_1_1,
                Description.empty(),
                false,
                ServerURI.of("tcp://localhost:1883"),
                ClientAuth.of(false, AuthUsername.empty(), AuthPassword.empty()),
                TimeInMs.zero(),
                TimeInMs.zero(),
                ConnectionError.empty(),
                ScriptId.empty(),
                ScriptId.empty()
        ));
    }

    public static CreateClientUseCase.ClientDto createClientForCreateClientUseCase(){
        return new CreateClientUseCase.ClientDto(
                CLIENT_NAME_1_1,
                PROJECT_ID_1,
                Description.empty(),
                ServerURI.of("tcp://localhost:1883"),
                ClientAuth.of(false, AuthUsername.empty(), AuthPassword.empty()),
                ScriptId.empty(),
                ScriptId.empty()
        );
    }

    public static ImportClientsUseCase.ClientDto createClient1ForImportClientsUseCase() {
        return new ImportClientsUseCase.ClientDto(
                CLIENT_ID_1_1,
                PROJECT_ID_1,
                CLIENT_NAME_1_1,
                Description.empty(),
                ServerURI.of("tcp://localhost:1883"),
                ClientAuth.of(false, AuthUsername.empty(), AuthPassword.empty()),
                ScriptId.empty(),
                ScriptId.empty()
        );
    }

    public static ImportClientsUseCase.ClientDto createClient2ForImportClientsUseCase() {
        return new ImportClientsUseCase.ClientDto(
                CLIENT_ID_2_1,
                PROJECT_ID_1,
                CLIENT_NAME_2_1,
                Description.empty(),
                ServerURI.of("tcp://localhost:1883"),
                ClientAuth.of(false, AuthUsername.empty(), AuthPassword.empty()),
                ScriptId.empty(),
                ScriptId.empty()
        );
    }

    public static UpdateClientUseCase.ClientDto createClientForUpdateClientUseCase(ClientName clientName) {
        return new UpdateClientUseCase.ClientDto(
                CLIENT_ID_1_1,
                PROJECT_ID_1,
                clientName,
                Description.empty(),
                ServerURI.of("tcp://localhost:1883"),
                ClientAuth.of(false, AuthUsername.empty(), AuthPassword.empty()),
                ScriptId.empty(),
                ScriptId.empty()
        );
    }
}
