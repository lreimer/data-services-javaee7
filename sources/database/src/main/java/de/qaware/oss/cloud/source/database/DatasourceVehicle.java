package de.qaware.oss.cloud.source.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vehicle")
public class DatasourceVehicle {

    @Id
    @Column(name = "vin", nullable = false)
    private String vin;

    public DatasourceVehicle() {
    }

    public DatasourceVehicle(String vin) {
        this.vin = vin;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }
}
