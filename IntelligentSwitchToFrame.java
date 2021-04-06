  /*
	 * @Project Name : Test
	 * @Function Name : dwn_sub_intelligentSwitchToFrame()
	 * @Description : Find and switch to frame
	 * @Input Param : ByXpath
	 * @Return : True or False
	 * @Date : DD-MM-YYYY
	 * @Author :  KG
	 *******************************************************************************
	 */
  public Boolean dwn_sub_intelligentSwitchToFrame(By byXpathObj, String reportObjName)
	{
		return dwn_sub_intelligentSwitchToFrame_Internal(byXpathObj,null,reportObjName);
	}
  
	public Boolean dwn_sub_intelligentSwitchToFrame(String strXpathObj, String reportObjName)
	{
		return dwn_sub_intelligentSwitchToFrame_Internal(null,strXpathObj,reportObjName);
	}
  
  /*
	 * @Project Name : Test
	 * @Function Name : dwn_sub_intelligentSwitchToFrame_Internal()
	 * @Description : Find and switch to frame
	 * @Input Param : ByXpath, StrXpath, Element, ObjNameForReport
	 * @Return : True or False
	 * @Date : DD-MM-YYYY
	 * @Author :  KG
	 *******************************************************************************
	 */
	public Boolean dwn_sub_intelligentSwitchToFrame_Internal(By byXpathObj, String strXpathObj, String reportObjName)
	{
		Boolean flag=false, flagNoFrame=false;
		String framename=null;
		String cframename=null;
		String frameNameFound=null;
    
		// If the parameter is string xpath, then convert it to By.Xpath
		if(byXpathObj==null)
		{
			if(strXpathObj!=null) {
				byXpathObj = By.xpath(strXpathObj);
			}
			else
			{
        System.out.println("Null is not a valid parameter for dwn_sub_intelligentSwitchToFrame_Internal");
			}
		}

		try
		{
			Driver.switchTo().defaultContent();  // Switch to default
			List<WebElement> eleNoFrame = Driver.findElements(byXpathObj);  // Getting the list in main page - No frame
			//System.out.println("No Frame - Size of element :" + eleNoFrame.size());

			if(eleNoFrame.size()==0) {
				List<WebElement> ele = Driver.findElements(By.tagName("iframe"));  // Getting the total frames available in the page
        
				//System.out.println("Number of frames in page :" + ele.size());
				for (WebElement el : ele) {
					framename = el.getAttribute("id");
					if(framename.length()==0)  // if frame id is empty, then get frame name
						framename = el.getAttribute("name");

					//System.out.println("Frame id/name :" + framename);

					if(framename.length()>0)
					{ // ignore frame switch if frame id is empty
						Driver.switchTo().frame(framename); // switch to the frame one by one
						//System.out.println("current frame :" + framename);
						//Check if element is present after frame switch
						try {
							if (Driver.findElement(byXpathObj).isDisplayed()) {
								flag = true;
								break; //Break the loop if element found in frame before switching to child
							}
						} catch (Exception e) 
            {
							List<WebElement> ele1 = Driver.findElements(By.tagName("iframe")); // Check if it has any child frames
							if (ele1.size() > 0) 
              {
								//System.out.println("Number of frames within " + framename + " : " + ele1.size());
								for (WebElement el1 : ele1) {
									//									System.out.println("Child frame id :" + el1.getAttribute("id"));
									cframename = el1.getAttribute("id");
									Driver.switchTo().frame(cframename); // switch to the child frame one by one
									//										System.out.println("current child frame :" + cframename);
									try 
                  {
										if (Driver.findElement(byXpathObj).isDisplayed()) {
											flag = true;
											break; //Break the loop if element found in child frame
										}
									} catch (Exception ex) 
                  {
										flag = false;
										Driver.switchTo().defaultContent(); // If element is not found, then switch to parent frame for next loop
										Driver.switchTo().frame(framename);
										//System.out.println("Current parent frame :" + framename);
									}
								}// for loop
								if (flag == true)
									break;

							}
							Driver.switchTo().defaultContent();
							//System.out.println("Switching to default content");
						}
					}

				} // for loop
			}
			else
			{
				flag = true;
				flagNoFrame=true;
				// Below report log will be commented once the method is stabilized
				//System.out.println("Element:" + reportObjName + " is found in the frame:" + framename);
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception in intelligentSwitchToFrame_Internal *************************** ");
			System.out.println(e.getMessage());
			flag = false;
		}

		if((flag)&(!flagNoFrame))
		{
			// Below report log will be commented once the method is stabilized
			System.out.println("Element:" + reportObjName + " is found in the frame:" + framename);
		}
		else if(!flag)
		{
			System.out.println("Element:" + reportObjName + " is NOT found in the any of the available frame");
		}
		return flag;
	}
  
  
  
