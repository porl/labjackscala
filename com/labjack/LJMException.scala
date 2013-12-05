package com.labjack

import com.sun.jna._

/**
 * LJMException is a custom exception class for the LJM object. It takes error
 * and errorAddress values returned from LJM library function calls, will
 * get the string error from the LJM library and setup exception strings with
 * them.
 *
 * @author LabJack Corporation <support@labjack.com>
 *
 * Ported to Scala from Java by porl <porl42@gmail.com>
 */
class LJMException(error: Int = 0, errorAddress: Int = -1) extends RuntimeException {
  private var errorString = ""
  private var message = ""

  setErrorString()
  setMessage()

  def getError = error

  def getErrorAddress = errorAddress

  def getErrorString = error // porl: Should this return the string?

  override def toString = getClass.getName + ": " + message

  override def getMessage = message

  private def setErrorString() {
    try {
      val errorStringPtr: Pointer = new Memory(LJM.Constants.MAX_NAME_SIZE)
      LJM.errorToString(error, errorStringPtr)
      errorString = errorStringPtr.getString(0)
    }
    catch {
      case e: Exception =>
        errorString = ""
    }
  }

  private def setMessage() {
    message = "LJM error " + error
    if (!errorString.isEmpty) {
      message += " " + errorString
    }
    if (errorAddress >= 0) {
      message += " at address " + errorAddress
    }
  }


}