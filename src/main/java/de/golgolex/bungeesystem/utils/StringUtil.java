package de.golgolex.bungeesystem.utils;

/*
===========================================================================================================================
# 
# Copyright (c) 2021 Pascal Kurz
# Class created at 22.08.2021, 20:50
# Class created by: Pascal
# 
# Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation 
# files (the "Software"),
# to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, 
# distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software 
# is furnished to do so, subject to the following conditions:
# 
# The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
# 
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
# INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE 
# AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
#  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
# OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
# 
===========================================================================================================================
*/

import java.util.concurrent.ThreadLocalRandom;

public class StringUtil {
    private final char[] VALUES = new char[] {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    public final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    public String randomString(int size) {
        StringBuilder stringBuilder = new StringBuilder();
        for (short i = 0; i < size; i++) {
            stringBuilder.append(VALUES[RANDOM.nextInt(VALUES.length)]);
        }
        return stringBuilder.substring(0);
    }
}
