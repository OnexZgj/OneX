package com.example.linsa.retrofitdemo.util;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Hutils {
	private final static String myKey="PMS2.0PMS2.0PMS2.0PMS2.0PMS2.0";

	/**
	 * 自定义Toast
	 * 
	 * @param context
	 * @param text
	 * @param duration
	 */
//	public static void bigToast(Context ct, String text, int duration) {
//		LayoutInflater inflater= (LayoutInflater) ct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		View toastRoot = inflater.inflate(R.layout.toast,null);
//		TextView message = (TextView) toastRoot.findViewById(R.id.message);
//		message.setText(text);
//		Toast toastStart = new Toast(ct);
//		toastStart.setGravity(Gravity.CENTER, 0, -100);
//		toastStart.setDuration(duration);
//		toastStart.setView(toastRoot);
//		toastStart.show();
//	}
	
	/**
	 * ps：XML 中配置的tag必须大写
	 * @param instanceBean
	 * @param view
	 * @param formate
	 * @throws SQLException
	 */
	public static <T> void fromBeanToXml( T instanceBean,
			View view,String formate){
		Method[] methods = instanceBean.getClass().getMethods();
		try {
			for (Method m : methods) {
				String name = m.getName().toUpperCase(Locale.getDefault());
				View childView = null;
				if (name.startsWith("GET")) {
					// methodMap.put(name.substring(3), m);
					childView = view.findViewWithTag(name.substring(3));

				} else if (name.startsWith("IS")) {
					// methodMap.put(name.substring(2), m);
					childView = view.findViewWithTag(name.substring(2));
				}

				// TODO 判断不同的View类型进行处理
				if (childView != null && childView instanceof TextView) {
					TextView et = (TextView) childView;
					Object getTextFromBean = m.invoke(instanceBean);
					if (getTextFromBean instanceof Date) {
						getTextFromBean = new SimpleDateFormat(formate,Locale.getDefault()).format(getTextFromBean);
					}
					if (getTextFromBean != null&& !getTextFromBean.toString().equals(""))
					
						et.setText(getTextFromBean.toString());
					else {
						et.setText("");
					}
				}

			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ps：XML 中配置的tag必须大写
	 * @param instanceBean
	 * @param view
	 * @param formate
	 * @throws SQLException
	 */
	public static <T> void fromBeanToXml( T instanceBean,
			View view,String formate , String ewm_bm){
		Method[] methods = instanceBean.getClass().getMethods();
		try {
			for (Method m : methods) {
				String name = m.getName().toUpperCase(Locale.getDefault());
				View childView = null;
				if (name.startsWith("GET")) {
					// methodMap.put(name.substring(3), m);
					childView = view.findViewWithTag(name.substring(3));

				} else if (name.startsWith("IS")) {
					// methodMap.put(name.substring(2), m);
					childView = view.findViewWithTag(name.substring(2));
				}

				// TODO 判断不同的View类型进行处理
				if (childView != null && childView instanceof TextView) {
					TextView et = (TextView) childView;
					Object getTextFromBean = m.invoke(instanceBean);
					if (getTextFromBean instanceof Date) {
						getTextFromBean = new SimpleDateFormat(formate,Locale.getDefault()).format(getTextFromBean);
					}
					if (getTextFromBean != null&& !getTextFromBean.toString().equals("")){
						//todo-lqy位置二维码不为空时，设备二维码不给值
						if((!TextUtils.isEmpty(ewm_bm))&&et.getTag().equals("SBBM")){
							et.setText("");
						}else{
							et.setText(getTextFromBean.toString());
						}
						
				    }else {
						et.setText("");
					}
				}

			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将XML中的数据同步到bean中
	 * 
	 * @param view
	 */
	public static <T> T FromXmlToBean(View view, 
			T instanceBean, SimpleDateFormat sdf) {
		if (instanceBean == null) {
			return null;
		}
		// bean.setxxx
		Method[] methods = instanceBean.getClass().getMethods();
		// T instanceBean = null;
		try {
			// instanceBean = bean.newInstance();
			for (Method _m : methods) {
				String name = _m.getName().toUpperCase(Locale.getDefault());
				if (name.startsWith("SET")) {
					View childView = view.findViewWithTag(name.substring(3));
					if (childView != null && childView instanceof TextView) {
						TextView et = (TextView) childView;
						//获取第一个形参的数据类型，如果没有形参
						Class<?> c = _m.getParameterTypes()[0];
						String etText = et.getText().toString();
						if (etText == null || "".equals(etText)||"点击输入时间".equals(etText)) {
							_m.invoke(instanceBean, new Object[]{null});
						} else {
							if (c.getName().equals("int")) {
								_m.invoke(instanceBean,
										Integer.parseInt(etText));
							} else if (c.getName().equals("long")) {
								_m.invoke(instanceBean, Long.parseLong(etText));
							} else if (c == Integer.class) {
								_m.invoke(instanceBean,
										(Integer) Integer.parseInt(etText));
							} else if (c == Double.class) {
								_m.invoke(instanceBean,
										(Double) Double.parseDouble(etText));
							} else if (c == Float.class) {
								_m.invoke(instanceBean,
										(Float) Float.parseFloat(etText));
							} else if (c == Long.class) {
								_m.invoke(instanceBean,
										(Long) Long.parseLong(etText));
							} else if (c == String.class) {
//								_m.invoke(instanceBean, stringParseStr(etText));
								_m.invoke(instanceBean, etText);
							} else if (c == Date.class) {
								String date = etText;
//								date = date.replaceFirst("T", " ");
								_m.invoke(instanceBean, sdf.parse(date));
							} else {
								try {
									throw new  Exception("Util方法中未找到类型");
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}

					}
				}
			}

		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return instanceBean;

	}
	
	/**
	 * 转义特殊字符串
	 * 
	 * @param s
	 * @return
	 */
	public static String stringParseStr(String s) {
		if (null == s) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			switch (c) {
			case '\"':
				sb.append("“");
				break;
			case '\\':
				sb.append("\\\\");
				break;
//			case '/':
//				sb.append("\\/");
//				break;
			case '@':
				sb.append(" ");
				break;
			case ';':
				sb.append("；");
				break;
			case '\'':
				sb.append("‘");
				break;
			default:
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 禁用所有子控件 除id之外
	 * @param viewGroup
	 */
	public static void disableSubControls(ViewGroup viewGroup,int id) {
		for (int i = 0; i < viewGroup.getChildCount(); i++) {
			View v = viewGroup.getChildAt(i);
			if(v.getId()==id)
				break;
			if (v instanceof ViewGroup) {
				if (v instanceof Spinner) {
					Spinner spinner = (Spinner) v;
					spinner.setClickable(false);
					spinner.setEnabled(false);

				} else if (v instanceof ListView) {
					((ListView) v).setClickable(false);
					((ListView) v).setEnabled(false);

				} else {
					disableSubControls((ViewGroup) v,id);
				}
			} else if (v instanceof EditText) {
				((EditText) v).setEnabled(false);
				((EditText) v).setClickable(false);

			} else if (v instanceof Button) {
				((Button) v).setEnabled(false);

			}else if (v instanceof TextView) {
				((TextView) v).setEnabled(false);

			}
		}
	}
	
	/**
	 * 非空验证
	 * @param value
	 * @return
	 */
	public static boolean commonNullCheck(String value){
		return TextUtils.isEmpty(value);
	}

}
