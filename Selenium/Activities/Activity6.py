# Import webdriver from selenium
from selenium import webdriver
from selenium.webdriver.common.by import By

# Start the Driver
with webdriver.Firefox() as driver:
    # Navigate to the URL
    driver.get("https://training-support.net/webelements/dynamic-controls")

    # Print the title of the page
    print("Page title is: ", driver.title)

	# Find the checkbox
    checkbox = driver.find_element(By.ID, "checkbox")
    # Click it
    checkbox.click()
    # Verify if the checkbox is selected or not
    print("Checkbox is visible: ", checkbox.is_selected())
    # Click it again
    checkbox.click()
    # Verify again if the checkbox is selected or not
    print("Checkbox is visible: ", checkbox.is_selected())