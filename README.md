# NFC 需求文件
## 運作原理:
![](https://i.imgur.com/mloP74R.png)
* src: https://www.slideshare.net/NFC-Forum/android-hce-an-intro-into-the-world-of-nfc-presentation-by-neel-rao-of-google-at-andevcon-2014-andevcon-android-app-developers-hce-nfc-tech-technologyan-devcon-presentation?next_slideshow=1
* HCE Example: https://www.slideshare.net/ChienMingChou/hce-tutorialv-v1
* **Important**:
**確定系上的NFC Reader會發出APDU的Command，不然無法運作。**


---

## Register AID
1. 需要跟廠商要求是哪種類別 (目前有payment, other)
2. 需要辨識那些AID
![](https://i.imgur.com/px8ppTc.png)


---
## 讀卡機傳來的APDU:格式 (可能需要跟廠商要他們的格式)
![](https://i.imgur.com/nFTVCx7.png)
* 確認APDU相同後做回應，不同APDU Command做不同回應。
* ![](https://i.imgur.com/x7pAoqZ.png)
* ![](https://i.imgur.com/RQwXpsV.png)



---

## Protocol
* ![](https://i.imgur.com/WhOgiEb.png)
* ![](https://i.imgur.com/BMefkzR.png)


---

## Data格式
![](https://i.imgur.com/nlg4TXp.png)


---
## 實際測試程式碼
### Card Reader
Data格式:
* ![](https://i.imgur.com/Sv7Ym1e.png)

傳輸Select AID Command:
* ![](https://i.imgur.com/0xuj16V.png)

接收到Card回應的消息，判斷正確就Request Card Number
* ![](https://i.imgur.com/3vW5b7q.png)

### Host Card Emulation:
![](https://i.imgur.com/zET1XWP.png)

* 用以判斷Card Reader傳來甚麼請求，並回傳對應資料
1. 是否指定AID正確後，回傳一個正確訊息，就可以開門?
2. 是否AID正確後，還會發出請求要求卡號，來開啟門禁?
