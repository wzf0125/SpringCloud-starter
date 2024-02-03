package org.quanta.selenium.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.quanta.selenium.config.SeleniumConfig;
import org.quanta.selenium.entity.Driver;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.time.Duration;
import java.util.List;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf
 * Date: 2024/2/2
 */
@Component
@RequiredArgsConstructor
public class ChromeDriverUtils {
    private final SeleniumConfig config;
    private ChromeOptions options;

    @PostConstruct
    private void loadDefaultConfig() {
        this.options = new ChromeOptions();
        // 填充配置
        if (CollUtil.isNotEmpty(config.getOptionsParam())) {
            List<String> optionsParam = config.getOptionsParam();
            optionsParam.forEach(param -> this.options.addArguments(param));
        }
        System.setProperty("webdriver.http.factory", "jdk-http-client");
        // 配置本地配置
        if (config.getLocal() != null && StrUtil.isNotBlank(config.getLocal().getDriverPath())) {
//            System.setProperty("webdriver.chrome.driver", config.getLocal().getDriverPath());
            System.setProperty("webdriver.chrome.driver", "D:\\environment\\chromedriver\\chromedriver.exe");
        }
        // 默认开启无头模式
        this.options.addArguments("--headless");
    }

    /**
     * 获取驱动
     * 默认优先本地配置
     * 本地配置不存在时才使用remote模式
     */
    public Driver getDriver() {
        return config.getLocal() != null ? getLocalDriver() : getRemoteDriver();
    }

    /**
     * 获取驱动
     * 默认优先本地配置
     * 本地配置不存在时才使用remote模式
     */
    public Driver getDriver(ChromeOptions options) {
        return config.getLocal() == null ? getLocalDriver(options) : getRemoteDriver(options);
    }

    /**
     * 获取本地selenium driver
     *
     * @return Driver对象
     */
    public Driver getLocalDriver() {
        return getLocalDriver(options, config.getWaitTIme());
    }

    /**
     * 获取本地selenium driver
     *
     * @param chromeOptions 浏览器配置
     * @return Driver对象
     */
    public Driver getLocalDriver(ChromeOptions chromeOptions) {
        return getLocalDriver(chromeOptions, config.getWaitTIme());
    }

    /**
     * 获取本地selenium driver
     *
     * @param chromeOptions 浏览器配置
     * @param waitTime      等待工具等待时间
     * @return Driver对象
     */
    public Driver getLocalDriver(ChromeOptions chromeOptions, Integer waitTime) {
        WebDriver driver = new ChromeDriver(chromeOptions);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
        return Driver.builder()
                .driver(driver)
                .wait(wait)
                .build();
    }

    /**
     * 获取远程selenium连接
     *
     * @return Driver对象
     */
    public Driver getRemoteDriver() {
        return getRemoteDriver(options, config.getWaitTIme());
    }

    /**
     * 获取远程selenium连接
     *
     * @param chromeOptions 浏览器配置
     * @return Driver对象
     */
    public Driver getRemoteDriver(ChromeOptions chromeOptions) {
        return getRemoteDriver(chromeOptions, config.getWaitTIme());
    }

    /**
     * 获取远程selenium连接
     *
     * @param chromeOptions 浏览器配置
     * @param waitTime      等待工具等待时间
     * @return Driver对象
     */
    @SneakyThrows
    public Driver getRemoteDriver(ChromeOptions chromeOptions, Integer waitTime) {
        WebDriver driver = new RemoteWebDriver(new URL(config.getRemote().getUrl()), chromeOptions);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
        return Driver.builder()
                .driver(driver)
                .wait(wait)
                .build();
    }
}
