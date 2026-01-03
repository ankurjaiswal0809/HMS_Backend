
package com.hms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DashboardStats {
 private long doctors;
 private long patients;
 private long appointmentsToday;
 private long medicines;
 private double todayRevenue;
}
