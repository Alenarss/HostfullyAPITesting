package Pojos;

public class PojosBooking {

    private String id;
    private String propertyId;
    private String startDate;
    private String endDate;
    private String guestName;

    public PojosBooking() {
    }

    public PojosBooking(String id, String propertyId, String startDate, String endDate, String guestName) {
        this.id = id;
        this.propertyId = propertyId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.guestName = guestName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    @Override
    public String toString() {
        return "PojosBooking{" +
                "id='" + id + '\'' +
                ", propertyId='" + propertyId + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", guestName='" + guestName + '\'' +
                '}';
    }
}
