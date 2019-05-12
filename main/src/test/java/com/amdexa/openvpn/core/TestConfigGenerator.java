/*
 * Copyright (c) 2012-2018 Arne Schwabe
 * Distributed under the GNU GPL v2 with additional terms. For full terms see the file doc/LICENSE.txt
 */

package com.amdexa.openvpn.core;

import android.content.Context;
import android.content.pm.PackageManager;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import com.amdexa.openvpn.VpnProfile;

import static com.amdexa.openvpn.VpnProfile.AUTH_RETRY_NOINTERACT;
import static com.amdexa.openvpn.VpnProfile.TYPE_USERPASS;

/**
 * Created by arne on 14.03.18.
 */

@Config(manifest= Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class TestConfigGenerator {
    @Test
    public void testAuthRetryGen() throws PackageManager.NameNotFoundException {
        /*Context mc = mock(Context.class);
        PackageManager mpm = mock(PackageManager.class);

        PackageInfo mpi = new PackageInfo();
        mpi.versionCode = 177;
        mpi.versionName = "foo";

        when(mc.getCacheDir()).thenReturn(new File("/j/unit/test/"));
        when(mc.getPackageName()).thenReturn("com.amdexa.openvpn");
        when(mc.getPackageManager()).thenReturn(mpm);
        when(mpm.getPackageInfo(eq("com.amdexa.openvpn"),eq(0))).thenReturn(mpi);*/



        VpnProfile vp = new VpnProfile ("test") {
            @Override
            public String getVersionEnvString(Context c) {
                return "no ver";
            }

            @Override
            public String getPlatformVersionEnvString() {
                return "test";
            }
        };

        vp.mAuthenticationType = TYPE_USERPASS;
        vp.mAuthRetry = AUTH_RETRY_NOINTERACT;
        String config = vp.getConfigFile(RuntimeEnvironment.application, false);
        Assert.assertTrue(config.contains("\nauth-retry nointeract\n"));
        for (Connection connection: vp.mConnections)
            Assert.assertTrue(connection.mProxyType == Connection.ProxyType.NONE);

    }


}
