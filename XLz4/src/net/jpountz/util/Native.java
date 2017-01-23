package net.jpountz.util;

/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.fusesource.hawtjni.runtime.Library;

/** FOR INTERNAL USE ONLY */
public enum Native {
  ;
  private static final Library LIBRARY = new Library("lz4-java", os().libExtension, Native.class);
  
  private enum OS {
    // Even on Windows, the default compiler from cpptasks (gcc) uses .so as a shared lib extension
    WINDOWS("win32", "so"), LINUX("linux", "so"), MAC("darwin", "dylib"), SOLARIS("solaris", "so");
    public final String name, libExtension;

    private OS(String name, String libExtension) {
      this.name = name;
      this.libExtension = libExtension;
    }
  }

  private final static OS os() {
    String osName = System.getProperty("os.name");
    if (osName.contains("Linux")) {
      return OS.LINUX;
    } else if (osName.contains("Mac")) {
      return OS.MAC;
    } else if (osName.contains("Windows")) {
      return OS.WINDOWS;
    } else if (osName.contains("Solaris") || osName.contains("SunOS")) {
      return OS.SOLARIS;
    } else {
      throw new UnsupportedOperationException("Unsupported operating system: "
          + osName);
    }
  }

  public static synchronized boolean isLoaded() {
    return LIBRARY.isLoaded();
  }

  public static synchronized void load() {
	  LIBRARY.load();
  }

}
