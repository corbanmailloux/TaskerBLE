/*
 * Copyright 2013 two forty four a.m. LLC <http://www.twofortyfouram.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * <http://www.apache.org/licenses/LICENSE-2.0>
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package co.corb.tasker_ble_plugin.bundle;

import android.os.Bundle;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import co.corb.tasker_ble_plugin.Constants;

/**
 * Tests {@link PluginBundleManager}.
 */
public final class PluginBundleManagerTest extends AndroidTestCase
{

    /**
     * Tests the extra constants haven't been accidentally changed.
     */
    @SmallTest
    public static void testExtraConstants()
    {
        /*
         * NOTE: This test is expected to fail initially when you are adapting this example to your own
         * plug-in. Once you've settled on constant names for your Intent extras, go ahead and update this
         * test case.
         * 
         * The goal of this test case is to prevent accidental renaming of the Intent extras. If the extra is
         * intentionally changed, then this unit test needs to be intentionally updated.
         */
        assertEquals("com.yourcompany.yourapp.extra.STRING_MESSAGE", PluginBundleManager.BUNDLE_EXTRA_STRING_MESSAGE); //$NON-NLS-1$
        assertEquals("com.yourcompany.yourcondition.extra.INT_VERSION_CODE", PluginBundleManager.BUNDLE_EXTRA_INT_VERSION_CODE); //$NON-NLS-1$
    }

    /**
     * Tests {@link PluginBundleManager#generateBundle(android.content.Context, boolean)} with the display on.
     */
    @SmallTest
    public void testGenerateBundle()
    {
        final Bundle bundle = PluginBundleManager.generateBundle(getContext(), "Foo"); //$NON-NLS-1$
        assertNotNull(bundle);

        assertEquals(2, bundle.keySet().size());

        assertEquals("Foo", bundle.getString(PluginBundleManager.BUNDLE_EXTRA_STRING_MESSAGE)); //$NON-NLS-1$
        assertEquals(Constants.getVersionCode(getContext()), bundle.getInt(PluginBundleManager.BUNDLE_EXTRA_INT_VERSION_CODE));
    }

    /**
     * Tests the {@link PluginBundleManager#isBundleValid(Bundle)} method with a correct bundle.
     */
    @SmallTest
    public static void testVerifyBundle_correct()
    {
        final Bundle bundle = new Bundle();
        bundle.putString(PluginBundleManager.BUNDLE_EXTRA_STRING_MESSAGE, "I am a toast message!"); //$NON-NLS-1$
        bundle.putInt(PluginBundleManager.BUNDLE_EXTRA_INT_VERSION_CODE, 1);
        assertTrue(PluginBundleManager.isBundleValid(bundle));
    }

    /**
     * Tests the {@link PluginBundleManager#isBundleValid(Bundle)} method with a null bundle
     */
    @SmallTest
    public static void testVerifyBundle_null()
    {
        assertFalse(PluginBundleManager.isBundleValid(null));
    }

    /**
     * Tests the {@link PluginBundleManager#isBundleValid(Bundle)} method with bundle that is missing a
     * required extra.
     */
    @SmallTest
    public static void testVerifyBundle_missing_extra()
    {
        assertFalse(PluginBundleManager.isBundleValid(new Bundle()));
    }

    /**
     * Tests the {@link PluginBundleManager#isBundleValid(Bundle)} method with bundle that contains extra
     * items.
     */
    @SmallTest
    public static void testVerifyBundle_extra_items()
    {
        final Bundle bundle = new Bundle();
        bundle.putString(PluginBundleManager.BUNDLE_EXTRA_STRING_MESSAGE, "I am a toast message!"); //$NON-NLS-1$
        bundle.putInt(PluginBundleManager.BUNDLE_EXTRA_INT_VERSION_CODE, 1);
        bundle.putString("test", "test"); //$NON-NLS-1$//$NON-NLS-2$
        assertFalse(PluginBundleManager.isBundleValid(bundle));
    }

    /**
     * Tests the {@link PluginBundleManager#isBundleValid(Bundle)} method with bundle that contains a null
     * message
     */
    @SmallTest
    public static void testVerifyBundle_null_message()
    {
        final Bundle bundle = new Bundle();
        bundle.putString(PluginBundleManager.BUNDLE_EXTRA_STRING_MESSAGE, null);
        bundle.putInt(PluginBundleManager.BUNDLE_EXTRA_INT_VERSION_CODE, 1);
        assertFalse(PluginBundleManager.isBundleValid(bundle));
    }

    /**
     * Tests the {@link PluginBundleManager#isBundleValid(Bundle)} method with bundle that contains an empty
     * message
     */
    @SmallTest
    public static void testVerifyBundle_empty_message()
    {
        final Bundle bundle = new Bundle();
        bundle.putString(PluginBundleManager.BUNDLE_EXTRA_STRING_MESSAGE, ""); //$NON-NLS-1$
        bundle.putInt(PluginBundleManager.BUNDLE_EXTRA_INT_VERSION_CODE, 1);
        assertFalse(PluginBundleManager.isBundleValid(bundle));
    }

    /**
     * Tests the {@link PluginBundleManager#isBundleValid(Bundle)} method with bundle that contains a mapping
     * to the wrong type
     */
    @SmallTest
    public static void testVerifyBundle_wrong_type()
    {
        {
            final Bundle bundle = new Bundle();
            bundle.putInt(PluginBundleManager.BUNDLE_EXTRA_STRING_MESSAGE, 1);
            bundle.putInt(PluginBundleManager.BUNDLE_EXTRA_INT_VERSION_CODE, 1);
            assertFalse(PluginBundleManager.isBundleValid(bundle));
        }

        {
            final Bundle bundle = new Bundle();
            bundle.putString(PluginBundleManager.BUNDLE_EXTRA_STRING_MESSAGE, "I am a toast message!"); //$NON-NLS-1$
            bundle.putString(PluginBundleManager.BUNDLE_EXTRA_INT_VERSION_CODE, "test"); //$NON-NLS-1$
            assertFalse(PluginBundleManager.isBundleValid(bundle));
        }
    }
}