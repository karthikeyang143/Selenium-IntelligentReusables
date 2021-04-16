/*
	 *******************************************************************************
	 * @Project Name : Project Name
	 * @Function Name : dwn_sub_intelligentWait()
	 * @Description : To wait for an element till given time
	 * @Input Param : element, waitTime, reportName
	 * @Return : true or false
	 * @Date : DD-MM-YYYY
	 * @Author :  Karthik
	 *******************************************************************************
	 */
	public Boolean dwn_sub_intelligentWait(String strXpathObj, String strWait, String reportObjName)
	{
		return dwn_sub_intelligentWait_Internal(null,strXpathObj,strWait, reportObjName);
	}
	public Boolean dwn_sub_intelligentWait(By byXpathObj, String strWait, String reportObjName)
	{
		return dwn_sub_intelligentWait_Internal(byXpathObj,null,strWait, reportObjName);
	}

	/*
	 *******************************************************************************
	 * @Project Name : Project Name
	 * @Function Name : dwn_sub_intelligentWait_WithoutFrame()
	 * @Description : To wait for an element till given time
	 * @Input Param : element, waitTime, reportName
	 * @Return : true or false
	 * @Date : DD-MM-YYYY
	 * @Author :  Karthik
	 *******************************************************************************
	 */
	public Boolean dwn_sub_intelligentWait_WithoutFrame(By byXpathObj, String strWait, String reportObjName)
	{
		return dwn_sub_intelligentWait_Internal_WithoutFrame(byXpathObj,null,strWait, reportObjName);
	}

	/*
	 *******************************************************************************
	 * @Project Name : Project Name
	 * @Function Name : dwn_sub_intelligentWait()
	 * @Description : To wait for an element display until the given time
	 * @Input Param : Element, int waitSeconds, reportObjName
	 * @Return : NA
	 * @Date : DD-MM-YYYY
	 * @Author :  Karthik
	 *******************************************************************************
	 */
	public Boolean dwn_sub_intelligentWait_Internal(By byXpathObj, String strXpathObj, String strWait, String reportObjName)
	{
		Boolean flag=false;
		int waitSeconds=0;
		try
		{
			//			waitForPageToBeReady();
			waitSeconds=dwn_sub_getWaitTimeInSeconds(strWait);
			//			System.out.println("======intelligentWait_Internal-waitSeconds :"+waitSeconds);
			for (int i = 1; i <= waitSeconds; i++)
			{
				//				System.out.println("for loop :"+i);
				// Try to switch to the frame by element, return true if success
				if(dwn_sub_intelligentSwitchToFrame_Internal(byXpathObj,strXpathObj,reportObjName))
				{
					flag = true;
					break;
				}
				else
				{
					// Wait 1 second if intelligentSwitchToFrame_Internal returns false
					Thread.sleep(1000);
				}
			}
		}
		catch (Exception e)
		{
			Report.updateTestLog("Exception", "Exception in dwn_sub_intelligentWait_Internal", Status.DEBUG);
			e.printStackTrace();
		}
		return flag;
	}

	/*
	 *******************************************************************************
	 * @Project Name : Project Name
	 * @Function Name : dwn_sub_intelligentWait_Internal_WithoutFrame()
	 * @Description : To wait for an element display until the given time
	 * @Input Param : Element, int waitSeconds, reportObjName
	 * @Return : NA
	 * @Date : DD-MM-YYYY
	 * @Author :  Karthik
	 *******************************************************************************
	 */
	public Boolean dwn_sub_intelligentWait_Internal_WithoutFrame(String strXpathObj, String strWait, String reportObjName)
	{
		Boolean flag=false;
		int waitSeconds=0;
		try
		{
			//			waitForPageToBeReady();
			waitSeconds=dwn_sub_getWaitTimeInSeconds(strWait);
			//			System.out.println("======intelligentWait_Internal-waitSeconds :"+waitSeconds);
			for (int i = 1; i <= waitSeconds; i++)
			{

				try {
					if (Driver.findElement(byXpathObj).isDisplayed()) {
						flag = true;
						break; //Break the loop if element found
					}
				} catch (Exception ex) {
					flag = false;
					Thread.sleep(1000);
				}
			}
		}
		catch (Exception e)
		{
			Report.updateTestLog("Exception", "Exception in dwn_sub_intelligentWait_Internal_WithoutFrame", Status.DEBUG);
			e.printStackTrace();
		}
		return flag;
	}
