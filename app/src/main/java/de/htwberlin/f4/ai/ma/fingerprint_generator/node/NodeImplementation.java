package de.htwberlin.f4.ai.ma.fingerprint_generator.node;

import android.media.Image;

import java.util.List;

/**
 * Created by Johann Winter
 */

class NodeImplementation implements Node {

    String id;
    //float xValue;
    //float yValue;
    String description;
    float zValue;
    List<SignalInformation> signalInformationList;
    String coordinates;
    //Image picture;
    String picturePath;

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void setSignalInformationList(List<SignalInformation> signalInformationList) {
        this.signalInformationList = signalInformationList;
    }

    @Override
    public String getCoordinates() {
        return this.coordinates;
    }

    @Override
    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public String getPicturePath() {
        return this.picturePath;
    }

    @Override
    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    /*
    @Override
    public Image getPicture() {
        return this.picture;
    }

    @Override
    public void setPicture(Image picture) {
        this.picture = picture;
    }
    */

    @Override
    public List<SignalInformation> getSignalInformation() {
        return this.signalInformationList;
    }



    public NodeImplementation(String id, float zValue, String description, List<SignalInformation> signalInformationList, String coordinates, String picturePath) {
        this.id = id;
        //this.xValue = xValue;
        //this.yValue = yValue;
        this.zValue = zValue;
        this.description = description;
        this.coordinates = coordinates;
        this.picturePath = picturePath;
        this.signalInformationList = signalInformationList;
    }

    public NodeImplementation(){}
}