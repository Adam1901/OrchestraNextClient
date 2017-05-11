package Views;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import dto.DTOBranch;
import dto.DTOServicePoint;

public class Controller {
	public List<DTOBranch> getBranches(LoginUser lu) throws UnirestException {
		List<DTOBranch> ret = new ArrayList<DTOBranch>();
		HttpResponse<JsonNode> asJson = Unirest.get("{ipPort}/rest/servicepoint/branches/")
				.routeParam("ipPort", lu.getServerIPPort())
				.basicAuth(lu.getUsername(), lu.getPassword()).asJson();

		JSONArray json = new JSONArray(asJson.getBody().toString());
		for (int i = 0; i < json.length(); i++) {
			JSONObject object = json.getJSONObject(i);
			DTOBranch fromJson = new Gson().fromJson(object.toString(), DTOBranch.class);
			ret.add(fromJson);
		}

		return ret;
	}

	public List<DTOServicePoint> getServicePoints(LoginUser lu, String branchId) throws UnirestException {
		List<DTOServicePoint> ret = new ArrayList<DTOServicePoint>();
		HttpResponse<JsonNode> asJson = Unirest
				.get("{ipPort}/rest/servicepoint/branches/{branchID}/servicePoints/deviceTypes/SW_SERVICE_POINT")
				.routeParam("branchID", branchId).routeParam("ipPort", lu.getServerIPPort())
				.basicAuth(lu.getUsername(), lu.getPassword()).asJson();

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
				.put("{ipPort}/rest/servicepoint/branches/{branchId}/servicePoints/{servicePointId}/users/superadmin/")
				.routeParam("branchID", branchId).routeParam("servicePointId", spId)
				.routeParam("ipPort", lu.getServerIPPort()).basicAuth(lu.getUsername(), lu.getPassword()).asJson();

		System.out.println(asJson.getStatus());
		System.out.println(asJson.getStatusText());
		System.out.println(asJson.getHeaders());
		System.out.println(asJson.getBody());
		return false;

	}

	public void callNext(LoginUser lu, String branchId, String spId) throws UnirestException {

		{
			HttpResponse<JsonNode> asJson = Unirest
					.post("{ipPort}/rest/servicepoint/branches/{branchID}/servicePoints/{servicePointId}/visits/next/")
					.routeParam("branchID", branchId).routeParam("servicePointId", spId).header("Allow", "POST")
					.routeParam("ipPort", lu.getServerIPPort()).basicAuth(lu.getUsername(), lu.getPassword()).asJson();

			System.out.println(asJson.getStatus());
			System.out.println(asJson.getStatusText());
			System.out.println(asJson.getHeaders());
			System.out.println(asJson.getBody());

			JSONObject object = new JSONObject(asJson.getBody());
			JSONObject visit = object.getJSONArray("visit").getJSONObject(1);
			System.out.println(visit.get("ticketId"));
		}

	}
}
