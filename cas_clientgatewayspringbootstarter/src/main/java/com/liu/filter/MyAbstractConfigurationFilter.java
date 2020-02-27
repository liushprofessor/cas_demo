/**
 * Created on [2020/2/26 9:58] by Administrator
 * <p>
 * 项目名称： cas TODO(项目名称)
 * <p>
 * 本程序版权属于福建慧政通信息科技有限公司所有。
 * 任何组织和个人未经福建慧政通信息科技有限公司许可与授权,不得擅自传播、复制、更改该程序的内容。
 * 本程序受版权法和国际条约的保护。如未经授权而擅自复制或传播本程序(或其中任何部分),
 * 将受到严厉的刑事及民事制裁，并将在法律许可的范围内受到最大可能的起诉!
 * <p>
 * ©2020 福建慧政通信息科技有限公司
 *//*

package com.liu.filter;

import org.jasig.cas.client.configuration.ConfigurationKey;
import org.jasig.cas.client.configuration.ConfigurationStrategy;
import org.jasig.cas.client.configuration.ConfigurationStrategyName;
import org.jasig.cas.client.util.ReflectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

*/
/**
 * 功能： TODO(用一句话描述类的功能)
 *
 * ──────────────────────────────────────────
 *   version  变更日期    修改人    修改说明
 * ------------------------------------------
 *   V1.0.0   2020/2/26    Liush     初版
 * ──────────────────────────────────────────
 *//*

public class MyAbstractConfigurationFilter {

    private static final String CONFIGURATION_STRATEGY_KEY = "configurationStrategy";
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private boolean ignoreInitConfiguration = false;
    private ConfigurationStrategy configurationStrategy;

    public AbstractConfigurationFilter() {
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        String configurationStrategyName = filterConfig.getServletContext().getInitParameter("configurationStrategy");
        this.configurationStrategy = (ConfigurationStrategy) ReflectUtils.newInstance(ConfigurationStrategyName.resolveToConfigurationStrategy(configurationStrategyName), new Object[0]);
        this.configurationStrategy.init(filterConfig, this.getClass());
    }

    protected final boolean getBoolean(ConfigurationKey<Boolean> configurationKey) {
        return this.configurationStrategy.getBoolean(configurationKey);
    }

    protected final String getString(ConfigurationKey<String> configurationKey) {
        return this.configurationStrategy.getString(configurationKey);
    }

    protected final long getLong(ConfigurationKey<Long> configurationKey) {
        return this.configurationStrategy.getLong(configurationKey);
    }

    protected final int getInt(ConfigurationKey<Integer> configurationKey) {
        return this.configurationStrategy.getInt(configurationKey);
    }

    protected final <T> Class<? extends T> getClass(ConfigurationKey<Class<? extends T>> configurationKey) {
        return this.configurationStrategy.getClass(configurationKey);
    }

    public final void setIgnoreInitConfiguration(boolean ignoreInitConfiguration) {
        this.ignoreInitConfiguration = ignoreInitConfiguration;
    }

    protected final boolean isIgnoreInitConfiguration() {
        return this.ignoreInitConfiguration;
    }
}
*/
