package Views;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import dto.DTOBranch;
import dto.DTOServicePoint;
import dto.DTOWorkProfile;

public class Controller {
	private final static Logger log = LogManager.getLogger(Controller.class);
	
	public List<DTOBranch> getBranches(LoginUser lu) throws UnirestException {
		List<DTOBranch> ret = new ArrayList<DTOBranch>();
		HttpResponse<JsonNode> asJson = Unirest.get(lu.getServerIPPort() + "/rest/servicepoint/branches/")
				.basicAuth(lu.getUsername(), lu.getPassword()).asJson();

		JSONArray json = new JSONArray(asJson.getBody().toString());
		for (int i = 0; i < json.length(); i++) {
			JSONObject object = json.getJSONObject(i);
			DTOBranch fromJson = new Gson().fromJson(object.toString(), DTOBranch.class);
			ret.add(fromJson);
		}

		return ret;
	}

	public List<DTOWorkProfile> getWorkProfile(LoginUser lu, String branchId) throws UnirestException {
		List<DTOWorkProfile> ret = new ArrayList<DTOWorkProfile>();
		HttpResponse<JsonNode> asJson = Unirest
				.get(lu.getServerIPPort() + "/rest/servicepoint/branches/{branchId}/workProfiles/")
				.routeParam("branchId", branchId).basicAuth(lu.getUsername(), lu.getPassword()).asJson();

		JSONArray json = new JSONArray(asJson.getBody().toString());
		for (int i = 0; i < json.length(); i++) {
			JSONObject object = json.getJSONObject(i);
			DTOWorkProfile fromJson = new Gson().fromJson(object.toString(), DTOWorkProfile.class);
			ret.add(fromJson);
		}

		return ret;
	}

	public List<DTOServicePoint> getServicePoints(LoginUser lu, String branchId) throws UnirestException {
		List<DTOServicePoint> ret = new ArrayList<DTOServicePoint>();
		HttpResponse<JsonNode> asJson = Unirest
				.get(lu.getServerIPPort()
						+ "/rest/servicepoint/branches/{branchID}/servicePoints/deviceTypes/SW_SERVICE_POINT")
				.routeParam("branchID", branchId).basicAuth(lu.getUsername(), lu.getPassword()).asJson();

		JSONArray json = new JSONArray(asJson.getBody().toString());
		for (int i = 0; i < json.length(); i++) {
			JSONObject object = json.getJSONObject(i);
			DTOServicePoint fromJson = new Gson().fromJson(object.toString(), DTOServicePoint.class);
			ret.add(fromJson);
		}

		return ret;

	}

	public boolean startSession(LoginUser lu, String branchId, String spId) throws UnirestException {

		HttpResponse<JsonNode> asJson = Unirest
				.put(lu.getServerIPPort()
						+ "/rest/servicepoint/branches/{branchId}/servicePoints/{servicePointId}/users/{username}/")
				.routeParam("branchId", branchId).routeParam("servicePointId", spId)
				.routeParam("username", lu.getUsername()).basicAuth(lu.getUsername(), lu.getPassword()).asJson();

		log.info(asJson.getStatus());
		log.info(asJson.getStatusText());
		log.info(asJson.getHeaders());
		log.info(asJson.getBody());
		return false;

	}

	public boolean endSession(LoginUser lu, String branchId, String spId) throws UnirestException {

		HttpResponse<JsonNode> asJson = Unirest
				.delete(lu.getServerIPPort()
						+ "/rest/servicepoint/branches/{branchId}/servicePoints/{servicePointId}/users/{username}/")
				.routeParam("branchId", branchId).routeParam("servicePointId", spId)
				.routeParam("username", lu.getUsername()).basicAuth(lu.getUsername(), lu.getPassword()).asJson();

		log.info(asJson.getStatus());
		log.info(asJson.getStatusText());
		log.info(asJson.getHeaders());
		log.info(asJson.getBody());
		return false;

	}

	public JSONObject callNext(LoginUser lu, String branchId, String spId) throws UnirestException {

		HttpResponse<JsonNode> asJson = Unirest
				.post(lu.getServerIPPort()
						+ "/rest/servicepoint/branches/{branchID}/servicePoints/{servicePointId}/visits/next/")
				.routeParam("branchID", branchId).routeParam("servicePointId", spId).header("Allow", "POST")
				.basicAuth(lu.getUsername(), lu.getPassword()).asJson();

		log.info(asJson.getStatus());
		log.info(asJson.getStatusText());
		log.info(asJson.getHeaders());
		log.info(asJson.getBody());

		JSONObject object = new JSONObject(asJson.getBody());

		return object;
	}

	public JSONObject recall(LoginUser lu, String branchId, String spId) throws UnirestException {

		HttpResponse<JsonNode> asJson = Unirest
				.put(lu.getServerIPPort()
						+ "/rest/servicepoint/branches/{branchID}/servicePoints/{servicePointId}/visit/recall/")
				.routeParam("branchID", branchId).routeParam("servicePointId", spId).header("Allow", "PUT")
				.basicAuth(lu.getUsername(), lu.getPassword()).asJson();

		log.info(asJson.getStatus());
		log.info(asJson.getStatusText());
		log.info(asJson.getHeaders());
		log.info(asJson.getBody());

		JSONObject object = new JSONObject(asJson.getBody());
		return object;
	}

	public void setWorkProfile(LoginUser lu, String branchId, String wpId) throws UnirestException {
		HttpResponse<JsonNode> asJson = Unirest
				.put(lu.getServerIPPort()
						+ "/rest/servicepoint/branches/{branchID}/users/{userName}/workProfile/{workProfileId}/")
				.routeParam("branchID", branchId).routeParam("userName", lu.getUsername())
				.routeParam("workProfileId", wpId).header("Allow", "PUT").basicAuth(lu.getUsername(), lu.getPassword())
				.asJson();
		log.info("SetWP");
		log.info(asJson.getStatus());
		log.info(asJson.getStatusText());
		log.info(asJson.getHeaders());
		log.info(asJson.getBody());
	}

	public void endVisit(LoginUser lu, String branchId, String visitId) throws UnirestException {
		HttpResponse<JsonNode> asJson = Unirest
				.put(lu.getServerIPPort()
						+ "/rest/servicepoint/branches/{branchID}/visits/{visitId}/end/")
				.routeParam("branchID", branchId).routeParam("visitId", visitId).header("Allow", "PUT")
				.basicAuth(lu.getUsername(), lu.getPassword()).asJson();
		log.info(asJson.getStatus());
		log.info(asJson.getStatusText());
		log.info(asJson.getHeaders());
		log.info(asJson.getBody());
		
	}
}
