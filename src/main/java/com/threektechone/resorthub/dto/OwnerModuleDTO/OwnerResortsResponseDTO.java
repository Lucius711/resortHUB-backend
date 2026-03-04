package com.threektechone.resorthub.dto.OwnerModuleDTO;

import java.time.LocalDateTime;

import com.threektechone.resorthub.enums.ResortStatus;

public class OwnerResortsResponseDTO {
    private int resortId;
    private String resortName;
    private String approvedByName;
    private String approvedByPhone;
    private ResortStatus resortStatus;
    private String location;
    private LocalDateTime createdAt;
    
    public OwnerResortsResponseDTO() {}

    public OwnerResortsResponseDTO(String resortName,String approvedByName,String approvedByPhone, ResortStatus resortStatus, String location, LocalDateTime createdAt ) {
        this.resortName = resortName;
        this.approvedByName = approvedByName;
        this.approvedByPhone = approvedByPhone;
        this.resortStatus= resortStatus;
        this.location = location;
        this.createdAt = createdAt;
    }

    public int getResortId() {
        return resortId;
    }

    public void setResortId(int resortId) {
        this.resortId = resortId;
    }

    public String getResortName() {
        return resortName;
    }

    public void setResortName(String resortName) {
        this.resortName = resortName;
    }

    public String getApprovedByName() {
        return approvedByName;
    }

    public void setApprovedByName(String approvedByName) {
        this.approvedByName = approvedByName;
    }

    public String getApprovedByPhone() {
        return approvedByPhone;
    }

    public void setApprovedByPhone(String approvedByPhone) {
        this.approvedByPhone = approvedByPhone;
    }

    public ResortStatus getResortStatus() {
        return resortStatus;
    }

    public void setResortStatus(ResortStatus resortStatus) {
        this.resortStatus = resortStatus;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
