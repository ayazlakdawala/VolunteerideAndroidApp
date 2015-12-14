package volunteeride.com.volunteerideandroidapp.dto;


import java.util.Date;

/**
 * Created by ayazlakdawala on 12/13/15.
 */
public class BaseDTOObject {

    protected String id;


    /**
     * This field is inserted with current date time in database using CreatedDate annotation.
     * This field will be ignored during deserialization and rendered during serialization.
     * See the Json annotations on this property, its getters and setters for more details.
     */
    protected Date createdDatetime;

    protected Long version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Date getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(Date createdDatetime) {
        this.createdDatetime = createdDatetime;
    }
}
