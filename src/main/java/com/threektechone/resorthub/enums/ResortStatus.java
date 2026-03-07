package com.threektechone.resorthub.enums;

public enum ResortStatus {
    DRAFT,           // owner đang nhập dở
    PENDING_REVIEW,  // đã submit, chờ staff duyệt
    APPROVED,        // staff đã duyệt
    CONTRACT_PENDING,// chờ ký hợp đồng
    ACTIVE,          // hoạt động
    REJECTED    
}
