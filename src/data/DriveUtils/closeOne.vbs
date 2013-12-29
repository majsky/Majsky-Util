Set wmp = CreateObject("WMPlayer.OCX")
Set cd = wmp.cdromCollection.getByDriveSpecifier(WScript.Arguments(0))
cd.Eject
cd.Eject