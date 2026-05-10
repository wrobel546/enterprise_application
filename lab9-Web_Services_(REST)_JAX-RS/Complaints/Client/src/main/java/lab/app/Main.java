package lab.app;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import lab.dto.ComplaintDTO;

public class Main {
    public static void main(String[] args) {
        String baseUrl = "http://localhost:8080/Server-1.0-SNAPSHOT/api/complaints";
        Client client = ClientBuilder.newClient();
        try {
            List<ComplaintDTO> all = client.target(baseUrl)
                    .request(MediaType.APPLICATION_JSON)
                    .get(new GenericType<List<ComplaintDTO>>() {});
            System.out.println("All: " + all);

            if (all.isEmpty()) {
                System.out.println("Brak skarg w bazie. Dodaj najpierw przez Postman lub POST.");
                return;
            }

            Long id = all.get(0).getId();
            Response r = client.target(baseUrl + "/" + id)
                    .request(MediaType.APPLICATION_JSON)
                    .get();
            if (r.getStatus() == 200) {
                ComplaintDTO single = r.readEntity(ComplaintDTO.class);
                System.out.println("Single: " + single);
                r.close();

                single.setStatus("closed");
                single.setId(id);
                Response putResp = client.target(baseUrl + "/" + id)
                        .request()
                        .put(Entity.entity(single, MediaType.APPLICATION_JSON));
                System.out.println("PUT result: HTTP " + putResp.getStatus());
                putResp.close();

                List<ComplaintDTO> open = client.target(baseUrl)
                        .queryParam("status", "open")
                        .request(MediaType.APPLICATION_JSON)
                        .get(new GenericType<List<ComplaintDTO>>() {});
                System.out.println("Open: " + open);

            } else {
                System.out.println("GET single returned HTTP " + r.getStatus());
                r.close();
            }
        } finally {
            client.close();
        }
    }
}
