# MCP Tools Real-Time Testing Guide

**Date:** October 5, 2025, 11:02 AM IST  
**Provider:** OpenRouter  
**Total Tools:** 99 MCP Tools Available

---

## 🎯 Testing Setup

### **1. Open Chat Interface:**
```
http://localhost:9099/chat
```

### **2. Select Provider:**
- Click provider dropdown
- Select: **OpenRouter**
- Start new conversation

---

## 🧪 Test Cases

### **Test 1: Google Search** 🔍

**Query:**
```
Search Google for "Spring Boot microservices tutorial"
```

**Expected:**
- AI should use `search_google` tool
- Return search results
- Show relevant links and descriptions

---

### **Test 2: Web Fetch** 🌐

**Query:**
```
Fetch the content from https://spring.io and summarize it
```

**Expected:**
- AI should use `fetch` tool
- Get webpage content
- Provide summary

---

### **Test 3: Date/Time** 📅

**Query:**
```
What is the current date and time?
```

**Expected:**
- AI should use `get_datetime` tool
- Return current date/time
- Format nicely

---

### **Test 4: Python Code Execution** 🐍

**Query:**
```
Execute this Python code: print("Hello from MCP!")
```

**Expected:**
- AI should use `run_python` tool
- Execute code
- Return output: "Hello from MCP!"

---

### **Test 5: Weather Information** 🌤️

**Query:**
```
What's the weather in New York?
```

**Expected:**
- AI should use weather-related tool
- Return current weather
- Include temperature, conditions

---

### **Test 6: Code Validation** ✅

**Query:**
```
Validate this Java code syntax: public class Test { public static void main(String[] args) { System.out.println("Hello"); } }
```

**Expected:**
- AI should use `validate_syntax` tool
- Check syntax
- Confirm if valid

---

### **Test 7: Multiple Tools** 🔧

**Query:**
```
Search Google for "OpenAI API", then fetch the first result and summarize it
```

**Expected:**
- AI should use multiple tools
- First: `search_google`
- Then: `fetch`
- Provide combined result

---

### **Test 8: Desktop Automation** 🖱️

**Query:**
```
Get the current mouse position
```

**Expected:**
- AI should use `mouse_pos` tool
- Return X, Y coordinates

---

### **Test 9: Clipboard Operations** 📋

**Query:**
```
Get the current clipboard content
```

**Expected:**
- AI should use `clip_get` tool
- Return clipboard text

---

### **Test 10: Health Check** 💚

**Query:**
```
Check the MCP server health status
```

**Expected:**
- AI should use `health` tool
- Return server status
- Show tool count

---

## 📝 Testing Checklist

### **For Each Test:**
- [ ] Type query in chat
- [ ] Click Send
- [ ] Wait for AI response
- [ ] Verify tool was used (check response)
- [ ] Verify result is correct
- [ ] Note any errors

### **Success Criteria:**
- ✅ AI recognizes when to use tools
- ✅ Tools execute successfully
- ✅ Results are accurate
- ✅ Response time is reasonable (< 10 seconds)
- ✅ No errors in console

---

## 🎯 Quick Test Sequence

### **Start Here:**

1. **Open Chat:**
   ```
   http://localhost:9099/chat
   ```

2. **Select OpenRouter Provider**

3. **Test 1 - Simple Search:**
   ```
   Search Google for "Spring Boot"
   ```

4. **Test 2 - Current Time:**
   ```
   What time is it now?
   ```

5. **Test 3 - Execute Code:**
   ```
   Run this Python code: print(2 + 2)
   ```

6. **Test 4 - Web Fetch:**
   ```
   Fetch https://example.com
   ```

7. **Test 5 - Multiple Tools:**
   ```
   Search for "OpenAI" and tell me the current time
   ```

---

## 📊 Expected Behavior

### **When AI Uses Tools:**

**You'll see in the response:**
```
I'll search Google for that...
[Tool: search_google]
Results: ...
```

Or:
```
Let me fetch that webpage...
[Tool: fetch]
Content: ...
```

### **Tool Usage Indicators:**
- AI mentions using a tool
- Results are formatted
- Source is cited

---

## 🔍 Debugging

### **If Tools Don't Work:**

1. **Check Console (F12):**
   - Look for errors
   - Check network requests

2. **Check MCP Server:**
   ```powershell
   Invoke-WebRequest -Uri "http://localhost:9099/api/mcp/tools" -UseBasicParsing
   ```

3. **Verify Provider:**
   - Ensure OpenRouter is selected
   - Check API key is configured

4. **Check Logs:**
   - Server logs for errors
   - Tool execution status

---

## ✅ Success Indicators

**Tools are working when:**
- ✅ AI mentions using specific tools
- ✅ Results match expected output
- ✅ No error messages
- ✅ Response includes tool data
- ✅ Multiple tools can be chained

---

## 🎉 Test Results Template

### **Test: [Tool Name]**
- **Query:** [What you asked]
- **Expected:** [What should happen]
- **Actual:** [What happened]
- **Status:** ✅ Pass / ❌ Fail
- **Notes:** [Any observations]

---

## 📞 Quick Reference

### **URLs:**
- Chat: http://localhost:9099/chat
- MCP Tools List: http://localhost:9099/mcp-tools
- API Endpoint: http://localhost:9099/api/mcp/tools

### **Provider:**
- **OpenRouter** (Selected for testing)

### **Total Tools:**
- **99 MCP Tools** available

---

**Ready to start testing! Open the chat and try the test queries above!** 🚀

---

**Created:** October 5, 2025, 11:02 AM IST  
**Status:** ✅ Ready for real-time testing
