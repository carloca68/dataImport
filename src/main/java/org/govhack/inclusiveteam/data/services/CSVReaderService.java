/*
 *
 *  Created by Carlos - carloca68@gmail.com
 *
 *  Copyright (c) 2013 GovHack 2013 - InclusiveTeam
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions
 *  are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *
 *  Neither the name of the project's author nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *  "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 *  LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 *  FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 *  HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 *  SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
 *  TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 *  PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *  LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * /
 */

package org.govhack.inclusiveteam.data.services;

import au.com.bytecode.opencsv.CSVReader;
import org.govhack.inclusiveteam.data.process.LineProcessor;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * The type CSV reader service.
 */
public class CSVReaderService {

    private String fileName;
    private char separator;
    private char quotechar;
    private int headerLine;
    private int startLine;
    private int maxLines;
    private LineProcessor processor;

    /**
     * Instantiates a new CSV reader service.
     *
     * @param fileName   the file name
     * @param separator  the separator
     * @param quotechar  the quotechar
     * @param headerLine the headerLine number
     * @param maxLines   the max lines
     * @param processor  the processor
     */
    public CSVReaderService(String fileName, char separator, char quotechar, int headerLine, int startLine, int maxLines, LineProcessor processor) {
        this.fileName = fileName;
        this.separator = separator;
        this.quotechar = quotechar;
        this.headerLine = headerLine;
        this.startLine = startLine;
        this.maxLines = maxLines;
        this.processor = processor;
    }

    /**
     * Read file.
     *
     * @throws FileNotFoundException the file not found exception
     */
    public void readFile() throws FileNotFoundException {
        int lineNumber = 0;
        int processedLines = 0;
        CSVReader reader = new CSVReader(new FileReader(fileName), separator, quotechar);
        String[] nextLine;
        try {
            while ((nextLine = reader.readNext()) != null) {
                lineNumber++;
                if (lineNumber >= startLine) {
                    processedLines++;
                    processor.processLine(processedLines, nextLine);
                }
                if (lineNumber == headerLine) {
                    processor.processHeaderLine(lineNumber, nextLine);
                }
                if (processedLines > maxLines) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}


