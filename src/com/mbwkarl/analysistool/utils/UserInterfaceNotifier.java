/*
 * Copyright 2015 Karl Birch.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mbwkarl.analysistool.utils;

import com.mbwkarl.analysistool.model.EntryAttribute;
import java.util.ArrayList;

/**
 *
 * @author Karl Birch
 */
public interface UserInterfaceNotifier {
    
    public void loadedFile(String filename);
    public void failedLoadFile(String filename);
    
    public void savedFile(String filename);
    public void failedSaveFile(String filename);

    public void mustLoadFile();

    // broadcast methods
    public void broadcastEntryCount(int entryCount);

    public void broadcastFormats(ArrayList<String> typesList);

    public void broadcastPropertyList(String[] proplist);

    public void broadcastDataStreams(ArrayList<com.mbwkarl.analysistool.model.DataStream> streams);

    public void broadcastCorruptions(int corruptionCount);

    public void broadcastFormatMap(String formatID, ArrayList<EntryAttribute> xAttributes, ArrayList<EntryAttribute> yAttributes);
    
}
