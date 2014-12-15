package com.solidmobile.ws.server;

import com.solidmobile.commons.log.ILogger;
import com.solidmobile.server.core.Register;
import com.solidmobile.server.core.SolidContext;
import com.solidmobile.server.meta.app.SolidServerApplication;

/**
 * @author Sven Jacobs
 */
@Register
public class WSServerApplication extends SolidServerApplication {

    public static final String APP_ID = "SM-WS";
    private ILogger log;

    @Override
    public void onInitialize(final SolidContext context) {
        initLogging(context);
        log.info("Server initialisiert.");
    }

    @Override
    public void onStart(final SolidContext context) {
        initLogging(context);
        log.info("Server gestartet.");
    }

    @Override
    public void onStop(final SolidContext context) {
    }

    @Override
    public void onUpgrade(final SolidContext context, final int oldVersion) {
    }

    @Override
    public String getAppId() {
        return APP_ID;
    }

    @Override
    public String getAppName() {
        return "SolidMobile-Workshop";
    }

    @Override
    public int getAppVersion() {
        return 1;
    }

    @Override
    public String getAppVersionDisplay() {
        return "1.0.0";
    }

    private void initLogging(final SolidContext context) {
        if (log == null) {
            log = context.loggerFactory().createLogger(WSServerApplication.class);
        }
    }
}
