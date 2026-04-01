package com.threektechone.resorthub.repositories.projection;

import com.threektechone.resorthub.enums.ResortStatus;

public interface ResortStatusCountProjection {
    ResortStatus getStatus();
    int getCount();
}
