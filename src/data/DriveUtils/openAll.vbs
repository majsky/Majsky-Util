Set wmp = CreateObject("WMPlayer.OCX")
Set cdroms = wmp.cdromCollection
if cdroms.Count >= 1 then
for i = 0 to cdroms.Count - 1
cdroms.Item(i).Eject
Next
End If