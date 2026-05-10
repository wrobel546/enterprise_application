package lab.dto;

import java.time.LocalDate;

public class ComplaintDTO {
    private Long id;
    private LocalDate complaintDate;
    private String complaintText;
    private String author;
    private String status;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getComplaintDate() { return complaintDate; }
    public void setComplaintDate(LocalDate complaintDate) { this.complaintDate = complaintDate; }

    public String getComplaintText() { return complaintText; }
    public void setComplaintText(String complaintText) { this.complaintText = complaintText; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "ComplaintDTO{" +
                "id=" + id +
                ", complaintDate=" + complaintDate +
                ", complaintText='" + complaintText + '\'' +
                ", author='" + author + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

}