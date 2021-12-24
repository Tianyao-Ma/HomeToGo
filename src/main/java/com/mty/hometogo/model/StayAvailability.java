package com.mty.hometogo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "stay_availability")
@JsonDeserialize(builder = StayAvailability.Builder.class)
public class StayAvailability implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private StayAvailabilityKey id;

    // @ManyToOne create a column function as foreignKey to connect with stay talbe;
    // however, we don't want to create an empty stay column, instead, we want to use stayId as the pk, so we
    // joincolumn, which is equal to delete the empty stay role;
    @MapsId("stay_id")
    @ManyToOne
    private Stay stay;
    private StayAvailabilityState state;

    public StayAvailability() {}

    public StayAvailability(Builder builder) {
        this.id = builder.id;
        this.stay = builder.stay;
        this.state = builder.state;
    }

    public StayAvailabilityKey getId() {
        return id;
    }

    public Stay getStay() {
        return stay;
    }

    public StayAvailabilityState getState() {
        return state;
    }

    public static class Builder {
        @JsonProperty("id")
        private StayAvailabilityKey id;

        @JsonProperty("stay")
        private Stay stay;

        @JsonProperty("state")
        private StayAvailabilityState state;

        public Builder setId(StayAvailabilityKey id) {
            this.id = id;
            return this;
        }

        public Builder setStay(Stay stay) {
            this.stay = stay;
            return this;
        }

        public Builder setState(StayAvailabilityState state) {
            this.state = state;
            return this;
        }

        public StayAvailability build() {
            return new StayAvailability(this);
        }
    }
}
