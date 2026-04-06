package com.threektechone.resorthub.repositories.projection;

import java.time.LocalDate;

public interface UserChartProjection {
    LocalDate getDate();
    int getCount();
}
