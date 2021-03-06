/*
 * This file is part of Network Spoofer for Android.
 * Network Spoofer lets you change websites on other people’s computers
 * from an Android phone.
 * Copyright (C) 2014 Will Shackleton <will@digitalsquid.co.uk>
 *
 * Network Spoofer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Network Spoofer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Network Spoofer, in the file COPYING.
 * If not, see <http://www.gnu.org/licenses/>.
 */

package uk.digitalsquid.netspoofer.config;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public final class ProcessRunner implements LogConf {
    private ProcessRunner() {}
    
    /**
     * Writes the <code>config</code> file with the env paramaters.
     * The config file is changed before the process is run now.
     * This is because of Android 4.3 not liking environment variables.
     * @param env
     */
    public static final void writeEnvConfigFile(Context context, Map<String, String> env) {
        HashMap<String, String> fullEnv = env == null ?
                new HashMap<String, String>() :
                new HashMap<String, String>(env);
        String scriptPath = FileInstaller.getScriptPath(context, "config");
        FileWriter writer = null;
        try {
            writer = new FileWriter(scriptPath, false);
            writer.write("# Config generated by writeEnvConfigFile\n");
            for(Entry<String, String> entry : fullEnv.entrySet()) {
                writer.write(String.format("export %s=\"%s\"\n", entry.getKey(), entry.getValue()));
            }
            writer.write("\n");
        } catch (FileNotFoundException e) {
            Log.e(TAG, "Failed to write env config file", e);
        } catch (IOException e) {
            Log.e(TAG, "Failed to write env config file", e);
        } finally {
            if(writer != null)
                try { writer.close(); } catch (IOException e) { }
        }
    }
}
