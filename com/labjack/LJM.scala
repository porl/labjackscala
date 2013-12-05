/**
 *
 */
package com.labjack

import com.sun.jna._
import com.sun.jna.win32.StdCallLibrary
import com.sun.jna.ptr._

/**
 * LJM is the wrapper object to the LJM library's functions and constants.
 * Refer to the LabJackM.h header file or online User's Guide for functions
 * and constants documentation:<br>
 * <br>
 * &nbsp;&nbsp;&nbsp;&nbsp;<a href="http://labjack.com/support/ljm/users-guide">http://labjack.com/support/ljm/users-guide</a><br>
 * <p>LJM library C to Scala differences:
 * <ul>
 * <li>C functions are implemented in the LJM object as static methods. The
 * function name's "LJM_" prefix have been removed and the first letter have
 * been changed to lowercase.
 * <li>C constants can be found in the LJM.Constants object. The constant name's
 * "LJM_" prefix have been removed.
 * <li>C error code constants can be found in the LJM.Errors object. The constant
 * name's "LJME_" prefix have been removed.
 * <li>C function parameter names have had the first letter changed to lowercase.
 * <li>If the wrapper method detects an error it will throw a LJMException
 * exception, setting the error and errorAddress values, and exception message.
 * <li>C parameters that are pass by reference are implemented in Scala as
 * arrays, or JNA classes IntByReference, DoubleByReference, or LongByReference
 * for single value references.
 * <li>C string parameters are implemented in Scala as a String and pass by
 * reference strings are a JNA class Pointer.<br>
 * <li>When using a JNA Pointer for C strings, the JNA Memory class needs to be used
 * to construct the pointer and allocate memory. For example:<br>
 * <br>
 * &nbsp;&nbsp;&nbsp;&nbsp;val stringPtr: Pointer = new Memory(100) //Allocates 100 bytes of memory which is a 100 character C string.
 * </ul>
 *
 * <p>Version History
 * <ul>
 * <li>0.91 - Added Linux and Mac OS X support.
 * <li>0.90 - Initial release tested with LJM v1.1.1. Windows support only.
 * </ul>
 * @author LabJack Corporation <support@labjack.com>
 * @version 0.91
 *
 * Ported to Scala from Java by porl <porl42@gmail.com>
 */

object LJM {
  private def handleError(error: Int) {
    if (error != Errors.NOERROR) {
      throw new LJMException(error)
    }
  }

  private def handleError(error: Int, errorAddress: Int) {
    if (error != Errors.NOERROR) throw new LJMException(error, errorAddress)
  }

  def listAll(deviceType: Int, connectionType: Int, numFound: IntByReference, aDeviceTypes: Array[Int], aConnectionTypes: Array[Int], aSerialNumbers: Array[Int], aIPAddresses: Array[Int]) = {
    val error = LabJackM.INSTANCE.LJM_ListAll(deviceType, connectionType, numFound, aDeviceTypes, aConnectionTypes, aSerialNumbers, aIPAddresses)
    handleError(error)
    error
  }

  def listAllS(deviceType: String, connectionType: String, numFound: IntByReference, aDeviceTypes: Array[Int], aConnectionTypes: Array[Int], aSerialNumbers: Array[Int], aIPAddresses: Array[Int]) = {
    val error = LabJackM.INSTANCE.LJM_ListAllS(deviceType, connectionType, numFound, aDeviceTypes, aConnectionTypes, aSerialNumbers, aIPAddresses)
    handleError(error)
    error
  }

  def openS(deviceType: String, connectionType: String, identifier: String, handle: IntByReference) = {
    val error = LabJackM.INSTANCE.LJM_OpenS(deviceType, connectionType, identifier, handle)
    handleError(error)
    error
  }

  def open(deviceType: Int, connectionType: Int, identifier: String, handle: IntByReference) = {
    val error = LabJackM.INSTANCE.LJM_Open(deviceType, connectionType, identifier, handle)
    handleError(error)
    error
  }

  def getHandleInfo(handle: Int, deviceType: IntByReference, connectionType: IntByReference, serialNumber: IntByReference, ipAddress: IntByReference, port: IntByReference, maxBytesPerMB: IntByReference) = {
    val error = LabJackM.INSTANCE.LJM_GetHandleInfo(handle, deviceType, connectionType, serialNumber, ipAddress, port, maxBytesPerMB)
    handleError(error)
    error
  }

  def close(handle: Int) = {
    val error = LabJackM.INSTANCE.LJM_Close(handle)
    handleError(error)
    error
  }

  def closeAll = {
    val error = LabJackM.INSTANCE.LJM_CloseAll
    handleError(error)
    error
  }

  def eWriteAddress(handle: Int, address: Int, `type`: Int, value: Double) = {
    val error = LabJackM.INSTANCE.LJM_eWriteAddress(handle, address, `type`, value)
    handleError(error)
    error
  }

  def eReadAddress(handle: Int, address: Int, `type`: Int, value: DoubleByReference) = {
    val error = LabJackM.INSTANCE.LJM_eReadAddress(handle, address, `type`, value)
    handleError(error)
    error
  }

  def eWriteName(handle: Int, name: String, value: Double) = {
    val error = LabJackM.INSTANCE.LJM_eWriteName(handle, name, value)
    handleError(error)
    error
  }

  def eReadName(handle: Int, name: String, value: DoubleByReference) = {
    val error = LabJackM.INSTANCE.LJM_eReadName(handle, name, value)
    handleError(error)
    error
  }

  def eReadAddresses(handle: Int, numFrames: Int, aAddresses: Array[Int], aTypes: Array[Int], aValues: Array[Double], errorAddress: IntByReference) = {
    val error = LabJackM.INSTANCE.LJM_eReadAddresses(handle, numFrames, aAddresses, aTypes, aValues, errorAddress)
    handleError(error, errorAddress.getValue)
    error
  }

  def eReadNames(handle: Int, numFrames: Int, aNames: Array[String], aValues: Array[Double], errorAddress: IntByReference) = {
    val error = LabJackM.INSTANCE.LJM_eReadNames(handle, numFrames, aNames, aValues, errorAddress)
    handleError(error, errorAddress.getValue)
    error
  }

  def eWriteAddresses(handle: Int, numFrames: Int, aAddresses: Array[Int], aTypes: Array[Int], aValues: Array[Double], errorAddress: IntByReference) = {
    val error = LabJackM.INSTANCE.LJM_eWriteAddresses(handle, numFrames, aAddresses, aTypes, aValues, errorAddress)
    handleError(error, errorAddress.getValue)
    error
  }

  def eWriteNames(handle: Int, numFrames: Int, aNames: Array[String], aValues: Array[Double], errorAddress: IntByReference) = {
    val error = LabJackM.INSTANCE.LJM_eWriteNames(handle, numFrames, aNames, aValues, errorAddress)
    handleError(error, errorAddress.getValue)
    error
  }

  def eAddresses(handle: Int, numFrames: Int, aAddresses: Array[Int], aTypes: Array[Int], aWrites: Array[Int], aNumValues: Array[Int], aValues: Array[Double], errorAddress: IntByReference) = {
    val error = LabJackM.INSTANCE.LJM_eAddresses(handle, numFrames, aAddresses, aTypes, aWrites, aNumValues, aValues, errorAddress)
    handleError(error, errorAddress.getValue)
    error
  }

  def eNames(handle: Int, numFrames: Int, aNames: Array[String], aWrites: Array[Int], aNumValues: Array[Int], aValues: Array[Double], errorAddress: IntByReference) = {
    val error = LabJackM.INSTANCE.LJM_eNames(handle, numFrames, aNames, aWrites, aNumValues, aValues, errorAddress)
    handleError(error, errorAddress.getValue)
    error
  }

  def eReadNameString(handle: Int, name: String, string: Pointer) = {
    val error = LabJackM.INSTANCE.LJM_eReadNameString(handle, name, string)
    handleError(error)
    error
  }

  def eReadAddressString(handle: Int, address: Int, string: Pointer) = {
    val error = LabJackM.INSTANCE.LJM_eReadAddressString(handle, address, string)
    handleError(error)
    error
  }

  def eWriteNameString(handle: Int, name: String, string: String) = {
    val error = LabJackM.INSTANCE.LJM_eWriteNameString(handle, name, string)
    handleError(error)
    error
  }

  def eWriteAddressString(handle: Int, address: Int, string: String) = {
    val error = LabJackM.INSTANCE.LJM_eWriteAddressString(handle, address, string)
    handleError(error)
    error
  }

  def eStreamStart(handle: Int, scansPerRead: Int, numAddresses: Int, aScanList: Array[Int], scanRate: DoubleByReference) = {
    val error = LabJackM.INSTANCE.LJM_eStreamStart(handle, scansPerRead, numAddresses, aScanList, scanRate)
    handleError(error)
    error
  }

  def eStreamRead(handle: Int, aData: Array[Double], deviceScanBacklog: IntByReference, ljmScanBacklog: IntByReference) = {
    val error = LabJackM.INSTANCE.LJM_eStreamRead(handle, aData, deviceScanBacklog, ljmScanBacklog)
    handleError(error)
    error
  }

  def eStreamStop(handle: Int) = {
    val error = LabJackM.INSTANCE.LJM_eStreamStop(handle)
    handleError(error)
    error
  }

  def writeRaw(handle: Int, data: Array[Byte], numBytes: Int) = {
    val error = LabJackM.INSTANCE.LJM_WriteRaw(handle, data, numBytes)
    handleError(error)
    error
  }

  def readRaw(handle: Int, data: Array[Byte], numBytes: Int) = {
    val error = LabJackM.INSTANCE.LJM_ReadRaw(handle, data, numBytes)
    handleError(error)
    error
  }

  def addressesToMBFB(maxBytesPerMBFB: Int, aAddresses: Array[Int], aTypes: Array[Int], aWrites: Array[Int], aNumValues: Array[Int], aValues: Array[Double], numFrames: IntByReference, aMBFBCommand: Array[Byte]) = {
    val error = LabJackM.INSTANCE.LJM_AddressesToMBFB(maxBytesPerMBFB, aAddresses, aTypes, aWrites, aNumValues, aValues, numFrames, aMBFBCommand)
    handleError(error)
    error
  }

  def mbfbComm(handle: Int, unitID: Byte, aMBFB: Array[Byte], errorAddress: IntByReference) = {
    val error = LabJackM.INSTANCE.LJM_MBFBComm(handle, unitID, aMBFB, errorAddress)
    handleError(error, errorAddress.getValue)
    error
  }

  def updateValues(aMBFBResponse: Array[Byte], aTypes: Array[Int], aWrites: Array[Int], aNumValues: Array[Int], numFrames: Int, aValues: Array[Double]) = {
    val error = LabJackM.INSTANCE.LJM_UpdateValues(aMBFBResponse, aTypes, aWrites, aNumValues, numFrames, aValues)
    handleError(error)
    error
  }

  def namesToAddresses(numFrames: Int, aNames: Array[String], aAddresses: Array[Int], aTypes: Array[Int]) = {
    val error = LabJackM.INSTANCE.LJM_NamesToAddresses(numFrames, aNames, aAddresses, aTypes)
    handleError(error)
    error
  }

  def nameToAddress(name: String, address: IntByReference, `type`: IntByReference) = {
    val error = LabJackM.INSTANCE.LJM_NameToAddress(name, address, `type`)
    handleError(error)
    error
  }

  def addressesToTypes(numAddresses: Int, aAddresses: Array[Int], aTypes: Array[Int]) = {
    val error = LabJackM.INSTANCE.LJM_AddressesToTypes(numAddresses, aAddresses, aTypes)
    handleError(error)
    error
  }

  def addressToType(address: Int, `type`: IntByReference) = {
    val error = LabJackM.INSTANCE.LJM_AddressToType(address, `type`)
    handleError(error)
    error
  }

  def errorToString(errorCode: Int, errorString: Pointer) {
    LabJackM.INSTANCE.LJM_ErrorToString(errorCode, errorString)
  }

  def loadConstants() {
    LabJackM.INSTANCE.LJM_LoadConstants()
  }

  def loadConstantsFromFile(fileName: String) = {
    val error = LabJackM.INSTANCE.LJM_LoadConstantsFromFile(fileName)
    handleError(error)
    error
  }

  def loadConstantsFromString(jsonString: String) = {
    val error = LabJackM.INSTANCE.LJM_LoadConstantsFromString(jsonString)
    handleError(error)
    error
  }

  def tcVoltsToTemp(tcType: Int, tcVolts: Double, cjTempK: Double, pTCTempK: DoubleByReference) = {
    val error = LabJackM.INSTANCE.LJM_TCVoltsToTemp(tcType, tcVolts, cjTempK, pTCTempK)
    handleError(error)
    error
  }

  def float32ToByteArray(aFLOAT32: Array[Float], registerOffset: Int, numFLOAT32: Int, aBytes: Array[Byte]) {
    LabJackM.INSTANCE.LJM_FLOAT32ToByteArray(aFLOAT32, registerOffset, numFLOAT32, aBytes)
  }

  def byteArrayToFLOAT32(aBytes: Array[Byte], registerOffset: Int, numFLOAT32: Int, aFLOAT32: Array[Float]) {
    LabJackM.INSTANCE.LJM_ByteArrayToFLOAT32(aBytes, registerOffset, numFLOAT32, aFLOAT32)
  }

  def uint16ToByteArray(aUINT16: Array[Short], registerOffset: Int, numUINT16: Int, aBytes: Array[Byte]) {
    LabJackM.INSTANCE.LJM_UINT16ToByteArray(aUINT16, registerOffset, numUINT16, aBytes)
  }

  def byteArrayToUINT16(aBytes: Array[Byte], registerOffset: Int, numUINT16: Int, aUINT16: Array[Short]) {
    LabJackM.INSTANCE.LJM_ByteArrayToUINT16(aBytes, registerOffset, numUINT16, aUINT16)
  }

  def uint32ToByteArray(aUINT32: Array[Int], registerOffset: Int, numUINT32: Int, aBytes: Array[Byte]) {
    LabJackM.INSTANCE.LJM_UINT32ToByteArray(aUINT32, registerOffset, numUINT32, aBytes)
  }

  def byteArrayToUINT32(aBytes: Array[Byte], registerOffset: Int, numUINT32: Int, aUINT32: Array[Int]) {
    LabJackM.INSTANCE.LJM_ByteArrayToUINT32(aBytes, registerOffset, numUINT32, aUINT32)
  }

  def int32ToByteArray(aINT32: Array[Int], registerOffset: Int, numINT32: Int, aBytes: Array[Byte]) {
    LabJackM.INSTANCE.LJM_INT32ToByteArray(aINT32, registerOffset, numINT32, aBytes)
  }

  def byteArrayToINT32(aBytes: Array[Byte], registerOffset: Int, numINT32: Int, aINT32: Array[Int]) {
    LabJackM.INSTANCE.LJM_ByteArrayToINT32(aBytes, registerOffset, numINT32, aINT32)
  }

  def numberToIP(number: Int, ipv4String: Pointer) = {
    val error = LabJackM.INSTANCE.LJM_NumberToIP(number, ipv4String)
    handleError(error)
    error
  }

  def ipToNumber(ipv4String: String, number: IntByReference) = {
    val error = LabJackM.INSTANCE.LJM_IPToNumber(ipv4String, number)
    handleError(error)
    error
  }

  def numberToMAC(number: Long, macString: Pointer) = {
    val error = LabJackM.INSTANCE.LJM_NumberToMAC(number, macString)
    handleError(error)
    error
  }

  def macToNumber(macString: String, number: LongByReference) = {
    val error = LabJackM.INSTANCE.LJM_MACToNumber(macString, number)
    handleError(error)
    error
  }

  def writeLibraryConfigS(parameter: String, value: Double) = {
    val error = LabJackM.INSTANCE.LJM_WriteLibraryConfigS(parameter, value)
    handleError(error)
    error
  }

  def writeLibraryConfigStringS(parameter: String, string: String) = {
    val error = LabJackM.INSTANCE.LJM_WriteLibraryConfigStringS(parameter, string)
    handleError(error)
    error
  }

  def readLibraryConfigS(parameter: String, value: DoubleByReference) = {
    val error = LabJackM.INSTANCE.LJM_ReadLibraryConfigS(parameter, value)
    handleError(error)
    error
  }

  def readLibraryConfigStringS(parameter: String, string: Pointer) = {
    val error = LabJackM.INSTANCE.LJM_ReadLibraryConfigStringS(parameter, string)
    handleError(error)
    error
  }

  def loadConfigurationFile(fileName: String) = {
    val error = LabJackM.INSTANCE.LJM_LoadConfigurationFile(fileName)
    handleError(error)
    error
  }

  def log(level: Int, string: String) = {
    val error = LabJackM.INSTANCE.LJM_Log(level, string)
    handleError(error)
    error
  }

  def resetLog = {
    val error = LabJackM.INSTANCE.LJM_ResetLog
    handleError(error)
    error
  }

  object LabJackM {
    val INSTANCE = Native.loadLibrary("LabJackM", if (Platform.isWindows) classOf[WindowsLabJackM] else classOf[LabJackM]).asInstanceOf[LabJackM]
  }

  trait LabJackM extends Library {
    def LJM_ListAll(DeviceType: Int, ConnectionType: Int, NumFound: IntByReference, aDeviceTypes: Array[Int], aConnectionTypes: Array[Int], aSerialNumbers: Array[Int], aIPAddresses: Array[Int]): Int

    def LJM_ListAllS(DeviceType: String, ConnectionType: String, NumFound: IntByReference, aDeviceTypes: Array[Int], aConnectionTypes: Array[Int], aSerialNumbers: Array[Int], aIPAddresses: Array[Int]): Int

    def LJM_OpenS(DeviceType: String, ConnectionType: String, Identifier: String, Handle: IntByReference): Int

    def LJM_Open(DeviceType: Int, ConnectionType: Int, Identifier: String, Handle: IntByReference): Int

    def LJM_GetHandleInfo(Handle: Int, DeviceType: IntByReference, ConnectionType: IntByReference, SerialNumber: IntByReference, IPAddress: IntByReference, Port: IntByReference, MaxBytesPerMB: IntByReference): Int

    def LJM_Close(Handle: Int): Int

    def LJM_CloseAll: Int

    def LJM_eWriteAddress(Handle: Int, Address: Int, Type: Int, Value: Double): Int

    def LJM_eReadAddress(Handle: Int, Address: Int, Type: Int, Value: DoubleByReference): Int

    def LJM_eWriteName(Handle: Int, Name: String, Value: Double): Int

    def LJM_eReadName(Handle: Int, Name: String, Value: DoubleByReference): Int

    def LJM_eReadAddresses(Handle: Int, NumFrames: Int, aAddresses: Array[Int], aTypes: Array[Int], aValues: Array[Double], ErrorAddress: IntByReference): Int

    def LJM_eReadNames(Handle: Int, NumFrames: Int, aNames: Array[String], aValues: Array[Double], ErrorAddress: IntByReference): Int

    def LJM_eWriteAddresses(Handle: Int, NumFrames: Int, aAddresses: Array[Int], aTypes: Array[Int], aValues: Array[Double], ErrorAddress: IntByReference): Int

    def LJM_eWriteNames(Handle: Int, NumFrames: Int, aNames: Array[String], aValues: Array[Double], ErrorAddress: IntByReference): Int

    def LJM_eAddresses(Handle: Int, NumFrames: Int, aAddresses: Array[Int], aTypes: Array[Int], aWrites: Array[Int], aNumValues: Array[Int], aValues: Array[Double], ErrorAddress: IntByReference): Int

    def LJM_eNames(Handle: Int, NumFrames: Int, aNames: Array[String], aWrites: Array[Int], aNumValues: Array[Int], aValues: Array[Double], ErrorAddress: IntByReference): Int

    def LJM_eReadNameString(Handle: Int, Name: String, string: Pointer): Int

    def LJM_eReadAddressString(Handle: Int, Address: Int, string: Pointer): Int

    def LJM_eWriteNameString(Handle: Int, Name: String, string: String): Int

    def LJM_eWriteAddressString(Handle: Int, Address: Int, string: String): Int

    def LJM_eStreamStart(Handle: Int, ScansPerRead: Int, NumAddresses: Int, aScanList: Array[Int], ScanRate: DoubleByReference): Int

    def LJM_eStreamRead(Handle: Int, aData: Array[Double], DeviceScanBacklog: IntByReference, LJMScanBacklog: IntByReference): Int

    def LJM_eStreamStop(Handle: Int): Int

    def LJM_WriteRaw(Handle: Int, Data: Array[Byte], NumBytes: Int): Int

    def LJM_ReadRaw(Handle: Int, Data: Array[Byte], NumBytes: Int): Int

    def LJM_AddressesToMBFB(MaxBytesPerMBFB: Int, aAddresses: Array[Int], aTypes: Array[Int], aWrites: Array[Int], aNumValues: Array[Int], aValues: Array[Double], NumFrames: IntByReference, aMBFBCommand: Array[Byte]): Int

    def LJM_MBFBComm(Handle: Int, UnitID: Byte, aMBFB: Array[Byte], ErrorAddress: IntByReference): Int

    def LJM_UpdateValues(aMBFBResponse: Array[Byte], aTypes: Array[Int], aWrites: Array[Int], aNumValues: Array[Int], NumFrames: Int, aValues: Array[Double]): Int

    def LJM_NamesToAddresses(NumFrames: Int, aNames: Array[String], aAddresses: Array[Int], aTypes: Array[Int]): Int

    def LJM_NameToAddress(Name: String, Address: IntByReference, Type: IntByReference): Int

    def LJM_AddressesToTypes(NumAddresses: Int, aAddresses: Array[Int], aTypes: Array[Int]): Int

    def LJM_AddressToType(Address: Int, Type: IntByReference): Int

    def LJM_ErrorToString(ErrorCode: Int, ErrorString: Pointer)

    def LJM_LoadConstants()

    def LJM_LoadConstantsFromFile(FileName: String): Int

    def LJM_LoadConstantsFromString(JsonString: String): Int

    def LJM_TCVoltsToTemp(TCType: Int, TCVolts: Double, CJTempK: Double, pTCTempK: DoubleByReference): Int

    def LJM_FLOAT32ToByteArray(aFLOAT32: Array[Float], RegisterOffset: Int, NumFLOAT32: Int, aBytes: Array[Byte])

    def LJM_ByteArrayToFLOAT32(aBytes: Array[Byte], RegisterOffset: Int, NumFLOAT32: Int, aFLOAT32: Array[Float])

    def LJM_UINT16ToByteArray(aUINT16: Array[Short], RegisterOffset: Int, NumUINT16: Int, aBytes: Array[Byte])

    def LJM_ByteArrayToUINT16(aBytes: Array[Byte], RegisterOffset: Int, NumUINT16: Int, aUINT16: Array[Short])

    def LJM_UINT32ToByteArray(aUINT32: Array[Int], RegisterOffset: Int, NumUINT32: Int, aBytes: Array[Byte])

    def LJM_ByteArrayToUINT32(aBytes: Array[Byte], RegisterOffset: Int, NumUINT32: Int, aUINT32: Array[Int])

    def LJM_INT32ToByteArray(aINT32: Array[Int], RegisterOffset: Int, NumINT32: Int, aBytes: Array[Byte])

    def LJM_ByteArrayToINT32(aBytes: Array[Byte], RegisterOffset: Int, NumINT32: Int, aINT32: Array[Int])

    def LJM_NumberToIP(Number: Int, IPv4String: Pointer): Int

    def LJM_IPToNumber(IPv4String: String, Number: IntByReference): Int

    def LJM_NumberToMAC(Number: Long, MACString: Pointer): Int

    def LJM_MACToNumber(MACString: String, Number: LongByReference): Int

    def LJM_WriteLibraryConfigS(Parameter: String, Value: Double): Int

    def LJM_WriteLibraryConfigStringS(Parameter: String, string: String): Int

    def LJM_ReadLibraryConfigS(Parameter: String, Value: DoubleByReference): Int

    def LJM_ReadLibraryConfigStringS(Parameter: String, string: Pointer): Int

    def LJM_LoadConfigurationFile(FileName: String): Int

    def LJM_Log(Level: Int, string: String): Int

    def LJM_ResetLog: Int
  }

  private trait WindowsLabJackM extends LabJackM with StdCallLibrary {
  }

  /**
   * Errors is an object containing the error constants from the LJM library.
   * The "LJME_" prefix have been removed from the original names.
   */
  object Errors {
    val NOERROR = 0
    val WARNINGS_BEGIN = 200 
    val WARNINGS_END = 399 
    val FRAMES_OMITTED_DUE_TO_PACKET_SIZE = 201 
    val DEBUG_LOG_FAILURE = 202 
    val USING_DEFAULT_CALIBRATION = 203 
    val MODBUS_ERRORS_BEGIN = 1200 
    val MODBUS_ERRORS_END = 1216 
    val MBE1_ILLEGAL_FUNCTION = 1201 
    val MBE2_ILLEGAL_DATA_ADDRESS = 1202 
    val MBE3_ILLEGAL_DATA_VALUE = 1203 
    val MBE4_SLAVE_DEVICE_FAILURE = 1204 
    val MBE5_ACKNOWLEDGE = 1205 
    val MBE6_SLAVE_DEVICE_BUSY = 1206 
    val MBE8_MEMORY_PARITY_ERROR = 1208 
    val MBE10_GATEWAY_PATH_UNAVAILABLE = 1210 
    val MBE11_GATEWAY_TARGET_NO_RESPONSE = 1211 
    val LIBRARY_ERRORS_BEGIN = 1220 
    val LIBRARY_ERRORS_END = 1399 
    val UNKNOWN_ERROR = 1221 
    val INVALID_DEVICE_TYPE = 1222 
    val INVALID_HANDLE = 1223 
    val DEVICE_NOT_OPEN = 1224 
    val STREAM_NOT_INITIALIZED = 1225 
    val DEVICE_NOT_FOUND = 1227 
    val DEVICE_ALREADY_OPEN = 1229 
    val COULD_NOT_CLAIM_DEVICE = 1230 
    val CANNOT_CONNECT = 1231 
    val SOCKET_LEVEL_ERROR = 1233 
    val CANNOT_OPEN_DEVICE = 1236 
    val CANNOT_DISCONNECT = 1237 
    val WINSOCK_FAILURE = 1238 
    val DEVICE_RECONNECT_FAILED = 1239 
    val INVALID_ADDRESS = 1250 
    val INVALID_CONNECTION_TYPE = 1251 
    val INVALID_DIRECTION = 1252 
    val INVALID_FUNCTION = 1253 
    val INVALID_NUM_REGISTERS = 1254 
    val INVALID_PARAMETER = 1255 
    val INVALID_PROTOCOL_ID = 1256 
    val INVALID_TRANSACTION_ID = 1257 
    val INVALID_VALUE_TYPE = 1259 
    val MEMORY_ALLOCATION_FAILURE = 1260 
    val NO_COMMAND_BYTES_SENT = 1261 
    val INCORRECT_NUM_COMMAND_BYTES_SENT = 1262 
    val NO_RESPONSE_BYTES_RECEIVED = 1263 
    val INCORRECT_NUM_RESPONSE_BYTES_RECEIVED = 1264 
    val MIXED_FORMAT_IP_ADDRESS = 1265 
    val UNKNOWN_IDENTIFIER = 1266 
    val NOT_IMPLEMENTED = 1267 
    val INVALID_INDEX = 1268 
    val INVALID_LENGTH = 1269 
    val ERROR_BIT_SET = 1270 
    val INVALID_MAXBYTESPERMBFB = 1271 
    val NULL_POINTER = 1272 
    val NULL_OBJ = 1273 
    val RESERVED_NAME = 1274 
    val UNPARSABLE_DEVICE_TYPE = 1275 
    val UNPARSABLE_CONNECTION_TYPE = 1276 
    val UNPARSABLE_IDENTIFIER = 1277 
    val PACKET_SIZE_TOO_LARGE = 1278 
    val TRANSACTION_ID_ERR = 1279 
    val PROTOCOL_ID_ERR = 1280 
    val LENGTH_ERR = 1281 
    val UNIT_ID_ERR = 1282 
    val FUNCTION_ERR = 1283 
    val STARTING_REG_ERR = 1284 
    val NUM_REGS_ERR = 1285 
    val NUM_BYTES_ERR = 1286 
    val CONFIG_FILE_NOT_FOUND = 1289 
    val CONFIG_PARSING_ERROR = 1290 
    val INVALID_NUM_VALUES = 1291 
    val CONSTANTS_FILE_NOT_FOUND = 1292 
    val INVALID_CONSTANTS_FILE = 1293 
    val INVALID_NAME = 1294 
    val OVERSPECIFIED_PORT = 1296 
    val INTENT_NOT_READY = 1297 
    val ATTR_LOAD_COMM_FAILURE = 1298 
    val INVALID_CONFIG_NAME = 1299 
    val ERROR_RETRIEVAL_FAILURE = 1300 
    val LJM_BUFFER_FULL = 1301 
    val COULD_NOT_START_STREAM = 1302 
    val STREAM_NOT_RUNNING = 1303 
    val UNABLE_TO_STOP_STREAM = 1304 
    val INVALID_VALUE = 1305 
    val SYNCHRONIZATION_TIMEOUT = 1306 
    val OLD_FIRMWARE = 1307
  }


  /**
   * Constants is an object containing the constants from the LJM library.
   * The "LJM_" prefix have been removed from the original names.
   */
  object Constants {
    val READ = 0
    val WRITE = 1
    val UINT16 = 0
    val UINT32 = 1
    val INT32 = 2
    val FLOAT32 = 3
    val BYTE = 99
    val STRING = 98
    val STRING_MAX_SIZE = 49
    val STRING_ALLOCATION_SIZE = 50
    val INVALID_NAME_ADDRESS = -1
    val MAX_NAME_SIZE = 256
    val MAC_STRING_SIZE = 18
    val IPv4_STRING_SIZE = 16
    val dtANY = 0
    val dtUE9 = 9
    val dtU3 = 3
    val dtU6 = 6
    val dtT7 = 7
    val dtSKYMOTE_BRIDGE = 1000
    val dtDIGIT = 200
    val ctANY = 0
    val ctUSB = 1
    val ctTCP = 2
    val ctETHERNET = 3
    val ctWIFI = 4
    val NO_IP_ADDRESS = 0
    val NO_PORT = 0
    val DEFAULT_PORT = 502
    val DEMO_MODE = "-1"
    val idANY = 0
    val DEFAULT_FEEDBACK_ALLOCATION_SIZE = 62
    val USE_DEFAULT_MAXBYTESPERMBFB = 0
    val LIST_ALL_SIZE = 128
    val MAX_USB_PACKET_NUM_BYTES = 64
    val MAX_TCP_PACKET_NUM_BYTES_T7 = 1040
    val MAX_TCP_PACKET_NUM_BYTES_NON_T7 = 64
    val NO_TIMEOUT = 0
    val DEFAULT_TIMEOUT = 20000
    val DUMMY_VALUE = -9999
    val GND = 199
    val ttB = 6001
    val ttE = 6002
    val ttJ = 6003
    val ttK = 6004
    val ttN = 6005
    val ttR = 6006
    val ttS = 6007
    val ttT = 6008
    val SEND_RECEIVE_TIMEOUT_MS = "LJM_SEND_RECEIVE_TIMEOUT_MS"
    val OPEN_TCP_DEVICE_TIMEOUT_MS = "LJM_OPEN_TCP_DEVICE_TIMEOUT_MS"
    val DEBUG_LOG_MODE = "LJM_DEBUG_LOG_MODE"
    val DEBUG_LOG_MODE_NEVER = 1.0
    val DEBUG_LOG_MODE_CONTINUOUS = 2.0
    val DEBUG_LOG_MODE_ON_ERROR = 3.0
    val DEBUG_LOG_LEVEL = "LJM_DEBUG_LOG_LEVEL"
    val STREAM_PACKET = 1.0
    val TRACE = 2.0
    val DEBUG = 4.0
    val INFO = 6.0
    val PACKET = 7.0
    val WARNING = 8.0
    val USER = 9.0
    val ERROR = 10.0
    val FATAL = 12.0
    val DEBUG_LOG_BUFFER_MAX_SIZE = "LJM_DEBUG_LOG_BUFFER_MAX_SIZE"
    val DEBUG_LOG_SLEEP_TIME_MS = "LJM_DEBUG_LOG_SLEEP_TIME_MS"
    val LIBRARY_VERSION = "LJM_LIBRARY_VERSION"
    val ALLOWS_AUTO_MULTIPLE_FEEDBACKS = "LJM_ALLOWS_AUTO_MULTIPLE_FEEDBACKS"
    val ALLOWS_AUTO_CONDENSE_ADDRESSES = "LJM_ALLOWS_AUTO_CONDENSE_ADDRESSES"
    val OPEN_MODE = "LJM_OPEN_MODE"
    val KEEP_OPEN = 1.0
    val OPEN_CLOSE = 2.0
    val MODBUS_MAP_CONSTANTS_FILE = "LJM_MODBUS_MAP_CONSTANTS_FILE"
    val ERROR_CONSTANTS_FILE = "LJM_ERROR_CONSTANTS_FILE"
    val DEBUG_LOG_FILE = "LJM_DEBUG_LOG_FILE"
    val CONSTANTS_FILE = "LJM_CONSTANTS_FILE"
    val DEBUG_LOG_FILE_MAX_SIZE = "LJM_DEBUG_LOG_FILE_MAX_SIZE"
    val STREAM_TRANSFERS_PER_SECOND = "LJM_STREAM_TRANSFERS_PER_SECOND"
    val RETRY_ON_TRANSACTION_ID_MISMATCH = "LJM_RETRY_ON_TRANSACTION_ID_MISMATCH"
    val OLD_FIRMWARE_CHECK = "LJM_OLD_FIRMWARE_CHECK"
  }

