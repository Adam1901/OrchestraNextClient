package controller;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import dto.*;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import utils.Props;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static utils.Props.getUserProperty;

/**

 * Created by Adam on 20/06/2017.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ControllerTest {
    private Controller cont = new Controller();
    private EntryPointController epCont = new EntryPointController();
    private LoginUser lu = null;

    @Before
    public void beforeTest() throws Exception {


        String ip = getUserProperty("ip");
        String port = getUserProperty("port");
        String proto = getUserProperty("proto");
        String username = getUserProperty("username");
        String password = Utils.decode(Props.getUserProperty("password"));

        String connectionString = proto + ip + ":" + port;

        lu = new LoginUser(username, password, connectionString);
    }

    //Work around because of spam limit of orchestra :(
    @After
    public void afterTEst() throws Exception {
        Thread.sleep(2000);
    }

    private <T> void testResponse(Pair<T> response) {
        assertEquals(200, response.getLeft().getStatus());
    }

    private <T> void testResponse(HttpResponse<T> response) {
        assertEquals(200, response.getStatus());
    }

    @Test
    public void aaastartSession() throws Exception {
        Pair<List<DTOBranch>> branches = cont.getBranches(lu);
        assertEquals(200, branches.getLeft().getStatus());
        List<DTOBranch> branch = branches.getValue();
        Pair<List<DTOServicePoint>> servicePointsP = cont.getServicePoints(lu, branch.get(0));
        assertEquals(200, servicePointsP.getLeft().getStatus());
        List<DTOServicePoint> servicePoints = servicePointsP.getValue();
        HttpResponse<JsonNode> jsonNodeHttpResponse = cont.startSession(lu, branch.get(0), servicePoints.get(0));
        assertEquals(200, jsonNodeHttpResponse.getStatus());
    }

    @Test
    public void getBranches() throws Exception {
        Pair<List<DTOBranch>> branchesP = cont.getBranches(lu);
        List<DTOBranch> branches = branchesP.getValue();
        assertFalse(branches.isEmpty());
    }

    @Test
    public void getWorkProfile() throws Exception {
        Pair<List<DTOBranch>> branche = cont.getBranches(lu);
        DTOBranch right = branche.getRight().get(0);
        Pair<List<DTOWorkProfile>> workProfile = cont.getWorkProfile(lu, right);
        assertFalse(workProfile.getValue().isEmpty());
        testResponse(workProfile);
    }

    @Test
    public void getServicePoints() throws Exception {
        Pair<List<DTOBranch>> branches = cont.getBranches(lu);
        testResponse(branches);
        Pair<List<DTOServicePoint>> servicePoints = cont.getServicePoints(lu, branches.getValue().get(0));
        testResponse(servicePoints);
        assertFalse(servicePoints.getValue().isEmpty());
    }

    @Test
    public void getEntryPoints() throws Exception {
        Pair<List<DTOEntryPoint>> entryPoints = epCont.getEntryPoints(lu, cont.getBranches(lu).getValue().get(0));
        testResponse(entryPoints);
        assertFalse(entryPoints.getValue().isEmpty());
    }

    /**
     * Not MAY FAIL IF VISITS ALREADY IN SYSTEM. PLEASE LOOK INTO IT.
     *
     * @throws Exception
     */
    @Test
    public void callNext() throws Exception {
        Pair<List<DTOBranch>> branches = cont.getBranches(lu);
        testResponse(branches);
        DTOBranch branchId = branches.getValue().get(0);
        Pair<List<DTOService>> services = epCont.getServices(lu, branchId);
        testResponse(services);
        Pair<List<DTOEntryPoint>> entryPoints = epCont.getEntryPoints(lu, branchId);
        testResponse(entryPoints);
        Pair<DTOVisit> visitCreate = epCont.createVisit(lu, branchId, entryPoints.getV().get(0),
                services.getValue().get(0));
        testResponse(visitCreate);
        assertNotNull(visitCreate.getValue());

        Pair<List<DTOServicePoint>> servicePoints = cont.getServicePoints(lu, branchId);
        testResponse(servicePoints);
        Pair<List<DTOWorkProfile>> workProfile = cont.getWorkProfile(lu, branchId);
        testResponse(workProfile);
        HttpResponse<DTOUserStatus> setWorkProfile = cont.setWorkProfile(lu, branchId, workProfile.getValue().get(0));
        testResponse(setWorkProfile);
        Pair<DTOUserStatus> dtoUserStatus = cont.callNext(lu, branchId, servicePoints.getV().get(0));
        testResponse(dtoUserStatus);
        visit visitCalled = dtoUserStatus.getValue().getVisit();
        assertNotNull(visitCalled);
        assertNotNull(visitCalled.getTicketId());

        assertEquals(visitCreate.getValue().getTicketId(), visitCalled.getTicketId());
        assertEquals(visitCreate.getValue().getId(), visitCalled.getId());

        HttpResponse<JsonNode> jsonNodeHttpResponse = cont.endVisit(lu, branchId,
                String.valueOf(visitCreate.getValue().getId()));
        assertEquals(200, jsonNodeHttpResponse.getStatus());


    }

    /**
     * Expected to fail on < UP8
     *
     * @throws Exception
     */
    @Test
    public void callNextAndEnd() throws Exception {
        Pair<List<DTOBranch>> branches = cont.getBranches(lu);
        testResponse(branches);
        DTOBranch branchId = branches.getV().get(0);
        Pair<List<DTOService>> services = epCont.getServices(lu, branchId);
        testResponse(services);

        Pair<List<DTOServicePoint>> servicePoints = cont.getServicePoints(lu, branchId);
        testResponse(servicePoints);
        HttpResponse<JsonNode> jsonNodeHttpResponse = cont.callNextAndEnd(lu, branchId, servicePoints.getV().get(0),
                services.getV().get(0).getId());
        assertEquals(200, jsonNodeHttpResponse.getStatus());
    }

    @Test
    public void recall() throws Exception {
        Pair<List<DTOBranch>> branches = cont.getBranches(lu);
        testResponse(branches);
        DTOBranch branchId = branches.getV().get(0);
        Pair<List<DTOService>> services = epCont.getServices(lu, branchId);
        testResponse(services);
        Pair<List<DTOEntryPoint>> entryPoints = epCont.getEntryPoints(lu, branchId);
        testResponse(entryPoints);
        Pair<DTOVisit> visitCreate = epCont.createVisit(lu, branchId, entryPoints.getV().get(0), services.getV().get(0));
        testResponse(visitCreate);
        assertNotNull(visitCreate.getValue().getTicketId());

        Pair<List<DTOServicePoint>> servicePoints = cont.getServicePoints(lu, branchId);
        testResponse(servicePoints);
        Pair<List<DTOWorkProfile>> workProfile = cont.getWorkProfile(lu, branchId);
        testResponse(workProfile);
        HttpResponse<DTOUserStatus> setWorkProfile = cont.setWorkProfile(lu, branchId, workProfile.getV().get(0));
        testResponse(setWorkProfile);
        Pair<DTOUserStatus> dtoUserStatus = cont.callNext(lu, branchId, servicePoints.getV().get(0));
        testResponse(dtoUserStatus);
        visit visitCalled = dtoUserStatus.getV().getVisit();
        assertNotNull(visitCalled);
        assertNotNull(visitCalled.getTicketId());

        assertEquals(visitCreate.getV().getTicketId(), visitCalled.getTicketId());
        assertEquals(visitCreate.getV().getId(), visitCalled.getId());

        // Wait so we can recall
        Thread.sleep(5000);
        Pair<DTOUserStatus> recall = cont.recall(lu, branchId, servicePoints.getV().get(0));
        testResponse(recall);
        assertNotNull(recall.getValue());
        assertNotNull(recall.getValue().getVisit());

        assertEquals(recall.getValue().getVisit().getTicketId(), visitCalled.getTicketId());
        assertEquals(recall.getValue().getVisit().getId(), visitCalled.getId());
    }

    @Test
    public void createVisit() throws Exception {
        createVisitLocal();
    }

    private void createVisitLocal() throws UnirestException {
        Pair<List<DTOBranch>> branches = cont.getBranches(lu);
        testResponse(branches);
        DTOBranch branchId = branches.getV().get(0);
        Pair<List<DTOService>> services = epCont.getServices(lu, branchId);
        testResponse(services);
        Pair<List<DTOEntryPoint>> entryPoints = epCont.getEntryPoints(lu, branchId);
        testResponse(entryPoints);
        Pair<DTOVisit> visitCreate = epCont.createVisit(lu, branchId, entryPoints.getV().get(0), services.getV().get(0));
        testResponse(visitCreate);
        assertNotNull(visitCreate.getValue().getTicketId());

        // now end the visit for the other tests :)
        Pair<List<DTOServicePoint>> servicePoints = cont.getServicePoints(lu, branchId);
        testResponse(servicePoints);
        Pair<List<DTOWorkProfile>> workProfile = cont.getWorkProfile(lu, branchId);
        testResponse(workProfile);
        HttpResponse<DTOUserStatus> jsonNodeHttpResponse1 = cont.setWorkProfile(lu, branchId, workProfile.getV().get(0));
        assertEquals(200, jsonNodeHttpResponse1.getStatus());
        Pair<DTOUserStatus> dtoUserStatus = cont.callNext(lu, branchId, servicePoints.getV().get(0));
        testResponse(dtoUserStatus);
        visit visitCalled = dtoUserStatus.getV().getVisit();
        assertNotNull(visitCalled);
        assertNotNull(visitCalled.getTicketId());

        assertEquals(visitCreate.getV().getTicketId(), visitCalled.getTicketId());
        assertEquals(visitCreate.getV().getId(), visitCalled.getId());

        HttpResponse<JsonNode> jsonNodeHttpResponse = cont.endVisit(lu, branchId, String.valueOf(visitCreate.getV().getId()));
        assertEquals(200, jsonNodeHttpResponse.getStatus());
    }

    @Test
    public void setWorkProfile() throws Exception {
        Pair<List<DTOBranch>> branches = cont.getBranches(lu);
        testResponse(branches);
        DTOBranch branchId = branches.getV().get(0);
        Pair<List<DTOService>> services = epCont.getServices(lu, branchId);
        testResponse(services);
        Pair<List<DTOEntryPoint>> entryPoints = epCont.getEntryPoints(lu, branchId);
        testResponse(entryPoints);
        Pair<DTOVisit> visitCreate = epCont.createVisit(lu, branchId, entryPoints.getV().get(0), services.getV().get(0));
        testResponse(visitCreate);
        assertNotNull(visitCreate.getValue().getTicketId());

        Pair<List<DTOServicePoint>> servicePoints = cont.getServicePoints(lu, branchId);
        testResponse(servicePoints);
        Pair<List<DTOWorkProfile>> workProfile = cont.getWorkProfile(lu, branchId);
        testResponse(workProfile);
        HttpResponse<DTOUserStatus> jsonNodeHttpResponse1 = cont.setWorkProfile(lu, branchId, workProfile.getV().get(0));
        assertEquals(200, jsonNodeHttpResponse1.getStatus());
        Pair<DTOUserStatus> dtoUserStatus = cont.callNext(lu, branchId, servicePoints.getV().get(0));
        testResponse(dtoUserStatus);
        visit visitCalled = dtoUserStatus.getV().getVisit();
        assertNotNull(visitCalled);
        assertNotNull(visitCalled.getTicketId());

        assertEquals(visitCreate.getV().getTicketId(), visitCalled.getTicketId());
        assertEquals(visitCreate.getV().getId(), visitCalled.getId());
        assertEquals(dtoUserStatus.getV().getWorkProfileId(), workProfile.getV().get(0).getId());
    }

    @Test
    public void endVisit() throws Exception {
        createVisitLocal();
    }

    @Test
    public void getQueueInfoForWorkprofile() throws Exception {
        Pair<List<DTOBranch>> branches = cont.getBranches(lu);
        testResponse(branches);
        DTOBranch branchId = branches.getV().get(0);
        Pair<List<DTOWorkProfile>> workProfile = cont.getWorkProfile(lu, branchId);
        testResponse(workProfile);
        DTOWorkProfile wpId = workProfile.getValue().get(0);
        HttpResponse<DTOUserStatus> jsonNodeHttpResponse1 = cont.setWorkProfile(lu, branchId, wpId);
        assertEquals(200, jsonNodeHttpResponse1.getStatus());
        Pair<List<DTOQueue>> queueInfoForWorkprofile = cont.getQueueInfoForWorkprofile(lu, branchId, wpId);
        testResponse(queueInfoForWorkprofile);
        assertFalse(queueInfoForWorkprofile.getV().isEmpty());

    }

    @Test
    public void getQueueInfo() throws Exception {
        Pair<List<DTOBranch>> branches = cont.getBranches(lu);
        testResponse(branches);
        DTOBranch branchId = branches.getV().get(0);
        Pair<List<DTOQueue>> queueInfoForWorkprofile = cont.getQueueInfo(lu, branchId);
        testResponse(queueInfoForWorkprofile);
        assertFalse(queueInfoForWorkprofile.getV().isEmpty());
    }

    @Test
    public void getServices() throws Exception {
        Pair<List<DTOBranch>> branches = cont.getBranches(lu);
        testResponse(branches);
        DTOBranch branchId = branches.getV().get(0);
        Pair<List<DTOService>> services = epCont.getServices(lu, branchId);
        testResponse(services);
        assertFalse(services.getV().isEmpty());
    }

    @Test
    public void sortAndOrderr() throws Exception {
        List<DTOService> obj = new ArrayList<>();
        String[] letters = {"A", "B", "C", "D", "E"};
        for (int i = 0; i < 5; i++) {
            DTOService service = new DTOService();
            service.setId(i);
            service.setExternalName(letters[i]);
            obj.add(service);
        }
        {
            OrchestraDTOSortable[] list = obj.toArray(new OrchestraDTOSortable[0]);
            List<DTOService> ts = Utils.sortAndRemove(list, true);
            for (int i = 0; i < ts.size(); i++) {
                assertEquals(letters[i],
                        ts.get(i).getName());
            }
        }
        {
            OrchestraDTOSortable[] list = obj.toArray(new OrchestraDTOSortable[0]);
            List<DTOService> ts = Utils.sortAndRemove(list, false);
            for (int i = 0; i < ts.size(); i++) {
                assertEquals(i, ts.get(i).getId());
            }
        }

    }

    @Test
    public void zzendSession() throws Exception {
        Pair<List<DTOBranch>> branches = cont.getBranches(lu);
        testResponse(branches);
        Pair<List<DTOServicePoint>> servicePoints = cont.getServicePoints(lu, branches.getV().get(0));
        testResponse(servicePoints);
        HttpResponse<JsonNode> jsonNodeHttpResponse = cont.endSession(lu, branches.getV().get(0),
                servicePoints.getValue().get(0));
        testResponse(jsonNodeHttpResponse);
        assertEquals(200, jsonNodeHttpResponse.getStatus());
    }

    @Test
    public void zzzlogout() throws Exception {
        HttpResponse<JsonNode> logout = cont.logout(lu);
        assertEquals(200, logout.getStatus());
    }
}