package org.quanta.selenium.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf
 * Date: 2024/2/2
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Driver {
    private WebDriver driver;
    private WebDriverWait wait;
}
