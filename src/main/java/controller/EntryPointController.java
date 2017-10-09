package controller;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import dto.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.Props;
import utils.Utils;

import java.util.List;

public class EntryPointController {
    private final static Logger log = LogManager.getLogger(EntryPointController.class);
    private final static boolean sortByName = Boolean
            .valueOf(Props.getGlobalProperty(Props.GlobalProperties.SORT_BY_NAME));

    public Pair<DTOVisit> createVisit(LoginUser lu, DTOBranch branch, DTOEntryPoint epId, DTOService service) throws UnirestException {
        HttpResponse<JsonNode> asJson = Unirest
                .post(lu.getServerIPPort()
                        + "/rest/entrypoint/branches/{branchID}/entryPoints/{entryPointId}/visits/")
                .routeParam("branchID", branch.getIdAsString())
                .routeParam("entryPointId", epId.getIdAsString())
                .header("Allow", "POST")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .basicAuth(lu.getUsername(), lu.getPassword())
                .body("{\"services\" : [" + service.getId() + "]}")
                .asJson();

        log.info(asJson.getStatus());
        log.info(asJson.getStatusText());
        log.info(asJson.getHeaders());
        log.info(asJson.getBody());

        JSONObject response = new JSONObject(asJson.getBody());
        JSONArray ar = response.getJSONArray("array");
        String moddedArray = ar.toString();
        moddedArray = moddedArray.substring(1);
        moddedArray = moddedArray.substring(0, moddedArray.length() - 1);

        DTOVisit vsist = new Gson().fromJson(moddedArray.toString(), DTOVisit.class);

        return new Pair<DTOVisit>(asJson, vsist);
    }

    public Pair<List<DTOEntryPoint>> getEntryPoints(LoginUser lu, DTOBranch branchId) throws UnirestException {
        HttpResponse<DTOEntryPoint[]> asObject = Unirest
                .get(lu.getServerIPPort()
                        + "/rest/entrypoint/branches/{branchID}/entryPoints/deviceTypes/SW_RECEPTION")
                .routeParam("branchID", branchId.getIdAsString())
                .basicAuth(lu.getUsername(), lu.getPassword())
                .asObject(DTOEntryPoint[].class);
        return new Pair<List<DTOEntryPoint>>(asObject, Utils.sortAndRemove(asObject.getBody(), sortByName));
    }

    public Pair<List<DTOService>> getServices(LoginUser lu, DTOBranch dtoBranch) throws UnirestException {
        HttpResponse<DTOService[]> asObject = Unirest
                .get(lu.getServerIPPort() + "/rest/entrypoint/branches/{branchID}/services/")
                .routeParam("branchID", dtoBranch.getIdAsString())
                .basicAuth(lu.getUsername(), lu.getPassword())
                .asObject(DTOService[].class);
        return new Pair<List<DTOService>>(asObject, Utils.sortAndRemove(asObject.getBody(), sortByName));
    }

}
