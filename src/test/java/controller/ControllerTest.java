package controller;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import dto.*;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import utils.Props;
import utils.Utils;

import java.util.List;

import static org.junit.Assert.*;
import static utils.Props.getUserProperty;

/**
 * Created by Adam on 20/06/2017.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ControllerTest {
    private Controller cont = new Controller();
    private LoginUser lu = null;

    @Before
    public void beforeTest() throws Exception {
        String ip = getUserProperty("ip");
        String port = getUserProperty("port");
        String proto = getUserProperty("proto");
        String username = getUserProperty("username");
        String password = Utils.decode(Props.getUserProperty("password"));

        String connectionString = proto + ip + ":"
                + port;

        lu = new LoginUser(username, password, connectionString);
    }

    @Test
    public void startSession() throws Exception {
        List<DTOBranch> branches = cont.getBranches(lu);
        List<DTOServicePoint> servicePoints = cont.getServicePoints(lu, branches.get(0));
        HttpResponse<JsonNode> jsonNodeHttpResponse = cont.startSession(lu, branches.get(0), servicePoints.get(0));
        assertEquals(200, jsonNodeHttpResponse.getStatus());
    }

    @Test
    public void getBranches() throws Exception {
        List<DTOBranch> branches = cont.getBranches(lu);
        assertFalse(branches.isEmpty());
    }

    @Test
    public void getWorkProfile() throws Exception {
        List<DTOWorkProfile> workProfile = cont.getWorkProfile(lu, cont.getBranches(lu).get(0));
        assertFalse(workProfile.isEmpty());
    }

    @Test
    public void getServicePoints() throws Exception {
        List<DTOServicePoint> servicePoints = cont.getServicePoints(lu, cont.getBranches(lu).get(0));
        assertFalse(servicePoints.isEmpty());
    }

    @Test
    public void getEntryPoints() throws Exception {
        List<DTOEntryPoint> entryPoints = cont.getEntryPoints(lu, cont.getBranches(lu).get(0));
        assertFalse(entryPoints.isEmpty());
    }

    /**
     * Not MAY FAIL IF VISITS ALREADY IN SYSTEM. PLEASE LOOK INTO IT.
     *
     * @throws Exception
     */
    @Test
    public void callNext() throws Exception {
        List<DTOBranch> branches = cont.getBranches(lu);
        DTOBranch branchId = branches.get(0);
        List<DTOService> services = cont.getServices(lu, branchId);
        List<DTOEntryPoint> entryPoints = cont.getEntryPoints(lu, branchId);
        DTOVisit visitCreate = cont.createVisit(lu, branchId, entryPoints.get(0), services.get(0));
        assertNotNull(visitCreate);

        List<DTOServicePoint> servicePoints = cont.getServicePoints(lu, branchId);
        List<DTOWorkProfile> workProfile = cont.getWorkProfile(lu, branchId);
        cont.setWorkProfile(lu, branchId, workProfile.get(0));
        DTOUserStatus dtoUserStatus = cont.callNext(lu, branchId, servicePoints.get(0));
        DTOUserStatus.Visit visitCalled = dtoUserStatus.getVisit();
        assertNotNull(visitCalled);
        assertNotNull(visitCalled.getTicketId());

        assertEquals(visitCreate.getTicketId(), visitCalled.getTicketId());
        assertEquals(visitCreate.getId(), visitCalled.getId());

        HttpResponse<JsonNode> jsonNodeHttpResponse = cont.endVisit(lu, branchId, String.valueOf(visitCreate.getId()));
        assertEquals(200, jsonNodeHttpResponse.getStatus());
    }

    /**
     * Expected to fail on < UP8
     *
     * @throws Exception
     */
    @Test
    public void callNextAndEnd() throws Exception {
        List<DTOBranch> branches = cont.getBranches(lu);
        DTOBranch branchId = branches.get(0);
        List<DTOService> services = cont.getServices(lu, branchId);
        List<DTOServicePoint> servicePoints = cont.getServicePoints(lu, branchId);
        HttpResponse<JsonNode> jsonNodeHttpResponse = cont.callNextAndEnd(lu, branchId, servicePoints.get(0), services.get(0).getId());
        assertEquals(200, jsonNodeHttpResponse.getStatus());
    }

    @Test
    public void recall() throws Exception {
        List<DTOBranch> branches = cont.getBranches(lu);
        DTOBranch branchId = branches.get(0);
        List<DTOService> services = cont.getServices(lu, branchId);
        List<DTOEntryPoint> entryPoints = cont.getEntryPoints(lu, branchId);
        DTOVisit visitCreate = cont.createVisit(lu, branchId, entryPoints.get(0), services.get(0));
        assertNotNull(visitCreate);

        List<DTOServicePoint> servicePoints = cont.getServicePoints(lu, branchId);
        List<DTOWorkProfile> workProfile = cont.getWorkProfile(lu, branchId);
        cont.setWorkProfile(lu, branchId, workProfile.get(0));
        DTOUserStatus dtoUserStatus = cont.callNext(lu, branchId, servicePoints.get(0));
        DTOUserStatus.Visit visitCalled = dtoUserStatus.getVisit();
        assertNotNull(visitCalled);
        assertNotNull(visitCalled.getTicketId());

        assertEquals(visitCreate.getTicketId(), visitCalled.getTicketId());
        assertEquals(visitCreate.getId(), visitCalled.getId());

        // Wait so we can recall
        Thread.sleep(5000);
        DTOUserStatus recall = cont.recall(lu, branchId, servicePoints.get(0));
        assertNotNull(recall);
        assertNotNull(recall.getVisit());

        assertEquals(recall.getVisit().getTicketId(), visitCalled.getTicketId());
        assertEquals(recall.getVisit().getId(), visitCalled.getId());
    }

    @Test
    public void createVisit() throws Exception {
        List<DTOBranch> branches = cont.getBranches(lu);
        DTOBranch branchId = branches.get(0);
        List<DTOService> services = cont.getServices(lu, branchId);
        List<DTOEntryPoint> entryPoints = cont.getEntryPoints(lu, branchId);
        DTOVisit visitCreate = cont.createVisit(lu, branchId, entryPoints.get(0), services.get(0));
        assertNotNull(visitCreate.getTicketId());



        //now end the visit for the other tests :)
        List<DTOServicePoint> servicePoints = cont.getServicePoints(lu, branchId);
        List<DTOWorkProfile> workProfile = cont.getWorkProfile(lu, branchId);
        HttpResponse<JsonNode> jsonNodeHttpResponse1 = cont.setWorkProfile(lu, branchId, workProfile.get(0));
        assertEquals(200, jsonNodeHttpResponse1.getStatus());
        DTOUserStatus dtoUserStatus = cont.callNext(lu, branchId, servicePoints.get(0));
        DTOUserStatus.Visit visitCalled = dtoUserStatus.getVisit();
        assertNotNull(visitCalled);
        assertNotNull(visitCalled.getTicketId());

        assertEquals(visitCreate.getTicketId(), visitCalled.getTicketId());
        assertEquals(visitCreate.getId(), visitCalled.getId());

        HttpResponse<JsonNode> jsonNodeHttpResponse = cont.endVisit(lu, branchId, String.valueOf(visitCreate.getId()));
        assertEquals(200, jsonNodeHttpResponse.getStatus());
    }

    @Test
    public void setWorkProfile() throws Exception {
        List<DTOBranch> branches = cont.getBranches(lu);
        DTOBranch branchId = branches.get(0);
        List<DTOService> services = cont.getServices(lu, branchId);
        List<DTOEntryPoint> entryPoints = cont.getEntryPoints(lu, branchId);
        DTOVisit visitCreate = cont.createVisit(lu, branchId, entryPoints.get(0), services.get(0));
        assertNotNull(visitCreate);

        List<DTOServicePoint> servicePoints = cont.getServicePoints(lu, branchId);
        List<DTOWorkProfile> workProfile = cont.getWorkProfile(lu, branchId);
        cont.setWorkProfile(lu, branchId, workProfile.get(0));
        DTOUserStatus dtoUserStatus = cont.callNext(lu, branchId, servicePoints.get(0));
        DTOUserStatus.Visit visitCalled = dtoUserStatus.getVisit();
        assertNotNull(visitCalled);
        assertNotNull(visitCalled.getTicketId());

        assertEquals(visitCreate.getTicketId(), visitCalled.getTicketId());
        assertEquals(visitCreate.getId(), visitCalled.getId());
        assertEquals(dtoUserStatus.getWorkProfileId(), workProfile.get(0).getId());
    }

    @Test
    public void endVisit() throws Exception {
        List<DTOBranch> branches = cont.getBranches(lu);
        DTOBranch branchId = branches.get(0);
        List<DTOService> services = cont.getServices(lu, branchId);
        List<DTOEntryPoint> entryPoints = cont.getEntryPoints(lu, branchId);
        DTOVisit visitCreate = cont.createVisit(lu, branchId, entryPoints.get(0), services.get(0));
        assertNotNull(visitCreate);

        List<DTOServicePoint> servicePoints = cont.getServicePoints(lu, branchId);
        List<DTOWorkProfile> workProfile = cont.getWorkProfile(lu, branchId);
        HttpResponse<JsonNode> jsonNodeHttpResponse1 = cont.setWorkProfile(lu, branchId, workProfile.get(0));
        assertEquals(200, jsonNodeHttpResponse1.getStatus());
        DTOUserStatus dtoUserStatus = cont.callNext(lu, branchId, servicePoints.get(0));
        DTOUserStatus.Visit visitCalled = dtoUserStatus.getVisit();
        assertNotNull(visitCalled);
        assertNotNull(visitCalled.getTicketId());

        assertEquals(visitCreate.getTicketId(), visitCalled.getTicketId());
        assertEquals(visitCreate.getId(), visitCalled.getId());

        HttpResponse<JsonNode> jsonNodeHttpResponse = cont.endVisit(lu, branchId, String.valueOf(visitCreate.getId()));
        assertEquals(200, jsonNodeHttpResponse.getStatus());
    }

    @Test
    public void getQueueInfoForWorkprofile() throws Exception {
        List<DTOBranch> branches = cont.getBranches(lu);
        DTOBranch branchId = branches.get(0);
        List<DTOWorkProfile> workProfile = cont.getWorkProfile(lu, branchId);
        DTOWorkProfile wpId = workProfile.get(0);
        HttpResponse<JsonNode> jsonNodeHttpResponse1 = cont.setWorkProfile(lu, branchId, wpId);
        assertEquals(200, jsonNodeHttpResponse1.getStatus());
        List<DTOQueue> queueInfoForWorkprofile = cont.getQueueInfoForWorkprofile(lu, branchId, wpId);
        assertFalse(queueInfoForWorkprofile.isEmpty());

    }

    @Test
    public void getQueueInfo() throws Exception {
        List<DTOBranch> branches = cont.getBranches(lu);
        DTOBranch branchId = branches.get(0);
        List<DTOQueue> queueInfoForWorkprofile = cont.getQueueInfo(lu, branchId);
        assertFalse(queueInfoForWorkprofile.isEmpty());
    }

    @Test
    public void getServices() throws Exception {
        List<DTOBranch> branches = cont.getBranches(lu);
        DTOBranch branchId = branches.get(0);
        List<DTOService> services = cont.getServices(lu, branchId);
        assertFalse(services.isEmpty());
    }

    @Test
    public void zzendSession() throws Exception {
        List<DTOBranch> branches = cont.getBranches(lu);
        List<DTOServicePoint> servicePoints = cont.getServicePoints(lu, branches.get(0));
        HttpResponse<JsonNode> jsonNodeHttpResponse = cont.endSession(lu, branches.get(0), servicePoints.get(0));
        assertEquals(200, jsonNodeHttpResponse.getStatus());
    }

    @Test
    public void zzzlogout() throws Exception {
        HttpResponse<JsonNode> logout = cont.logout(lu);
        assertEquals(200, logout.getStatus());
    }
}