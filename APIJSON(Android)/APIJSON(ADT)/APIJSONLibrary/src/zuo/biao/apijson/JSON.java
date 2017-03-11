/*Copyright ©2016 TommyLemon(https://github.com/TommyLemon/APIJSON)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package zuo.biao.apijson;

import java.util.List;

import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**阿里json封装类 防止解析时异常
 * @author Lemon
 */
public class JSON {
	private static final String TAG = "JSON";

	/**判断json格式是否正确
	 * @param s
	 * @return
	 */
	public static boolean isJsonCorrect(String s) {
		Log.i(TAG, "isJsonCorrect  <<<<     " + s + "     >>>>>>>");
		if (s == null 
				//				|| s.equals("[]") 
				//				|| s.equals("{}") 
				|| s.equals("")
				|| s.equals("[null]")
				|| s.equals("{null}")
				|| s.equals("null")) {
			return false;
		}
		return true;
	}

	/**获取有效的json
	 * @param s
	 * @return
	 */
	public static String getCorrectJson(String s) {
		return getCorrectJson(s, false);
	}
	/**获取有效的json
	 * @param s
	 * @param isArray
	 * @return
	 */
	public static String getCorrectJson(String s, boolean isArray) {
		s = StringUtil.getTrimedString(s);
		//		if (isArray) {
		//			if (s.startsWith("\"")) {
		//				s = s.substring(1);
		//			}
		//			if (s.endsWith("\"")) {
		//				s = s.substring(0, s.length() - 1);
		//			}
		//		}
		return s;//isJsonCorrect(s) ? s : null;
	}

	/**json转JSONObject
	 * @param json
	 * @return
	 */
	public static JSONObject parseObject(String json) {
		int features = com.alibaba.fastjson.JSON.DEFAULT_PARSER_FEATURE;
		features |= Feature.OrderedField.getMask();
		return parseObject(json, features);
	}
	/**json转JSONObject
	 * @param json
	 * @param features
	 * @return
	 */
	public static JSONObject parseObject(String json, int features) {
		try {
			return com.alibaba.fastjson.JSON.parseObject(getCorrectJson(json), JSONObject.class, features);
		} catch (Exception e) {
			Log.i(TAG, "parseObject  catch \n" + e.getMessage());
		}
		return null;
	}

	/**JSONObject转实体类
	 * @param object
	 * @param clazz
	 * @return
	 */
	public static <T> T parseObject(JSONObject object, Class<T> clazz) {
		return parseObject(toJSONString(object), clazz);
	}
	/**json转实体类
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> T parseObject(String json, Class<T> clazz) {
		try {
			int features = com.alibaba.fastjson.JSON.DEFAULT_PARSER_FEATURE;
			features |= Feature.OrderedField.getMask();
			return com.alibaba.fastjson.JSON.parseObject(getCorrectJson(json), clazz, features);
		} catch (Exception e) {
			Log.i(TAG, "parseObject  catch \n" + e.getMessage());
		}
		return null;
	}

	/**json转JSONArray
	 * @param json
	 * @return
	 */
	public static JSONArray parseArray(String json) {
		try {
			return com.alibaba.fastjson.JSON.parseArray(getCorrectJson(json, true));
		} catch (Exception e) {
			Log.i(TAG, "parseArray  catch \n" + e.getMessage());
		}
		return null;
	}
	/**JSONArray转实体类列表
	 * @param array
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> parseArray(JSONArray array, Class<T> clazz) {
		return parseArray(toJSONString(array), clazz);
	}
	/**json转实体类列表
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> parseArray(String json, Class<T> clazz) {
		try {
			return com.alibaba.fastjson.JSON.parseArray(getCorrectJson(json, true), clazz);
		} catch (Exception e) {
			Log.i(TAG, "parseArray  catch \n" + e.getMessage());
		}
		return null;
	}

	/**实体类转json
	 * @param obj
	 * @return
	 */
	public static String toJSONString(Object obj) {
		if (obj instanceof String) {
			return (String) obj;
		}
		try {
			return com.alibaba.fastjson.JSON.toJSONString(obj);
		} catch (Exception e) {
			Log.i(TAG, "toJSONString  catch \n" + e.getMessage());
		}
		return null;
	}

	/**实体类转json
	 * @param obj
	 * @param features
	 * @return
	 */
	public static String toJSONString(Object obj, SerializerFeature... features) {
		if (obj instanceof String) {
			return (String) obj;
		}
		try {
			return com.alibaba.fastjson.JSON.toJSONString(obj, features);
		} catch (Exception e) {
			Log.i(TAG, "toJSONString  catch \n" + e.getMessage());
		}
		return null;
	}

	/**格式化，显示更好看
	 * @param json
	 * @return
	 */
	public static String format(String json) {
		return format(parseObject(json));
	}
	/**格式化，显示更好看
	 * @param object
	 * @return
	 */
	public static String format(JSONObject object) {
		return toJSONString(object, SerializerFeature.PrettyFormat);
	}

	/**判断是否为JSONObject
	 * @param obj instanceof String ? parseObject
	 * @return
	 */
	public static boolean isJSONObject(Object obj) {
		if (obj instanceof JSONObject) {
			return true;
		}
		if (obj instanceof String) {
			try {
				JSONObject json = parseObject((String) obj);
				return json != null && json.isEmpty() == false;
			} catch (Exception e) {
				//太长 System.out.println(TAG + "select  while (rs.next()){  >> i = "
				//  + i + "  try { json = JSON.parse((String) value);"
				//	+ ">> } catch (Exception e) {\n" + e.getMessage());
			}
		}
		
		return false;
	}
	/**判断是否为JSONArray
	 * @param obj instanceof String ? parseArray
	 * @return
	 */
	public static boolean isJSONArray(Object obj) {
		if (obj instanceof JSONArray) {
			return true;
		}
		if (obj instanceof String) {
			try {
				JSONArray json = parseArray((String) obj);
				return json != null && json.isEmpty() == false;
			} catch (Exception e) {
				//太长 System.out.println(TAG + "select  while (rs.next()){  >> i = "
				//  + i + "  try { json = JSON.parse((String) value);"
				//	+ ">> } catch (Exception e) {\n" + e.getMessage());
			}
		}
		
		return false;
	}

}
