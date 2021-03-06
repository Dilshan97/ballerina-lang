/*
 * Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.ballerina.plugins.idea.inspections;

import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManager;
import com.intellij.openapi.extensions.PluginId;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.EditorNotificationPanel;
import com.intellij.ui.EditorNotifications;
import io.ballerina.plugins.idea.BallerinaFileType;
import io.ballerina.plugins.idea.sdk.BallerinaSdkService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Provides wrong module type message if the ballerina file is not in a Ballerina module.
 */
public class VersionMismatchNotificationProvider extends EditorNotifications.Provider<EditorNotificationPanel>
        implements DumbAware {

    private static final Key<EditorNotificationPanel> KEY = Key.create("Plugin Version Mismatch");
    private static final String BALLERINA_PLUGIN_ID = "io.ballerina";

    private final Project myProject;

    public VersionMismatchNotificationProvider(@NotNull Project project) {
        myProject = project;
    }

    @NotNull
    @Override
    public Key<EditorNotificationPanel> getKey() {
        return KEY;
    }

    @Override
    public EditorNotificationPanel createNotificationPanel(@NotNull VirtualFile file, @NotNull FileEditor fileEditor) {
        if (file.getFileType() != BallerinaFileType.INSTANCE) {
            return null;
        }
        Module module = ModuleUtilCore.findModuleForFile(file, myProject);
        if (module == null) {
            return null;
        }
        String sdkVersion = BallerinaSdkService.getInstance(myProject).getSdkVersion(module);
        String pluginVersion = getBallerinaPluginVersion();
        if (sdkVersion != null && pluginVersion != null) {
            if (!getMinorVersion(sdkVersion).equals(getMinorVersion(pluginVersion))) {
                return createPanel(module, sdkVersion, pluginVersion);
            }
        }
        return null;
    }

    @Nullable
    private static String getBallerinaPluginVersion() {
        IdeaPluginDescriptor balPluginDescriptor = PluginManager.getPlugin(PluginId.getId(BALLERINA_PLUGIN_ID));
        if (balPluginDescriptor != null) {
            return balPluginDescriptor.getVersion();
        }
        return null;
    }

    @NotNull
    private String getMinorVersion(@NotNull String version) {
        return version.contains(".") ? version.split("[.]")[1] : "";
    }

    @NotNull
    private static EditorNotificationPanel createPanel(@NotNull Module module, @NotNull String sdkVersion,
            @NotNull String pluginVersion) {
        EditorNotificationPanel panel = new EditorNotificationPanel();
        panel.setText("Ballerina SDK version (" + sdkVersion + ") of the module '" + module.getName()
                + "' does not match with the Ballerina plugin version (" + pluginVersion + "). Code insight features "
                + "might not work.");
        return panel;
    }
}
