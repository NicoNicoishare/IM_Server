## 消息格式

#### 描述

用户收发的消息都是如下格式的消息，使用JSON序列化。

为避免群ID与收件人ID重复，使用type字段区别群消息和个人消息



#### 格式

| 名称 | 类型   | 描述                                                        |
| ---- | ------ | ----------------------------------------------------------- |
| id   | Int    | 消息ID，发消息时无需此字段                                  |
| type | Int    | 0:个人文本消息，1:个人图片消息，2:群文本消息，3: 群图片消息 |
| text | String | 消息内容                                                    |
| to   | Int    | 收件人ID或群ID                                              |
| from | Int    | 发件人ID，发消息时无需此字段                                |
| time | Long   | 发送时间(时间戳)                                            |



------



## 建立连接

通过websocket建立连接。连接地址如下：

```
ws://ins.itstudio.club/conn?token=令牌值
```



------



## 发送消息

发送消息的格式如下：

```json
{
  "type": 0,
  "text": "Hello",
  "to": 2,
  "time": 1574844371011
}
```



------



## 接收消息

接收消息格式如下

```json
{
  "id": 7,
  "type": 0,
  "text": "Hello",
  "to": 2,
  "from": 4,
  "time": 1574844371011
}
```



------



## 创建群聊

描述：提供群头像、群名称创建多人聊天室，



#### 请求语法

```http
POST /im/group HTTP/1.1
Host: ins.itstudio.club
Authorization: Token 令牌值
```



#### 请求元素

| 名称    | 类型   | 是否必选 | 描述          |
| ------- | ------ | -------- | ------------- |
| name    | String | 是       | 群聊名称      |
| picture | String | 是       | 群头像图片URL |



#### 响应元素

| 名称    | 类型   | 描述     |
| ------- | ------ | -------- |
| status  | Int    | 状态码   |
| message | String | 状态信息 |



------



## 解散群聊

描述：创建者删除群聊，其他人无权限



#### 请求语法

```http
DELETE /im/group HTTP/1.1
Host: ins.itstudio.club
Authorization: Token 令牌值
```



#### 请求元素

| 名称    | 类型 | 是否必选 | 描述   |
| ------- | ---- | -------- | ------ |
| groupID | Int  | 是       | 群聊ID |



#### 响应元素

| 名称    | 类型   | 描述     |
| ------- | ------ | -------- |
| status  | Int    | 状态码   |
| message | String | 状态信息 |



#### 响应示例

```http
{
    "status": 200,
    "message": "success",
}
```



------



## 加入群聊

描述：用户通过群聊ID加入群聊



#### 请求语法

```http
PUT /im/group HTTP/1.1
Host: ins.itstudio.club
Authorization: Token 令牌值
```



#### 请求元素

| 名称    | 类型 | 是否必选 | 描述   |
| ------- | ---- | -------- | ------ |
| groupID | Int  | 是       | 群聊ID |



#### 响应元素

| 名称    | 类型   | 描述     |
| ------- | ------ | -------- |
| status  | Int    | 状态码   |
| message | String | 状态信息 |



------



## 获取群聊列表

描述：获取用户创建的或加入的群聊的列表



#### 请求语法

```http
GET /im/group/list HTTP/1.1
Host: ins.itstudio.club
Authorization: Token 令牌值
```



#### 响应元素

| 名称      | 类型   | 描述                                                 |
| --------- | ------ | ---------------------------------------------------- |
| status    | Int    | 状态码                                               |
| message   | String | 状态信息                                             |
| data      | Array  | 群资料列表子节点：id, creatorID, name, time, picture |
| id        | Int    | 群聊ID                                               |
| creatorID | Int    | 创建者用户ID                                         |
| name      | String | 群聊名                                               |
| time      | Long   | 群聊创建时间（时间戳）                               |
| picture   | String | 群头像URL                                            |



#### 响应示例

```json
{
    "status": 200,
    "message": "success",
    "data": [
        {
          "id": 2,
          "creatorID": 2,
          "name": "测试群聊一",
          "time": 1574844302138,
          "picture": "https://i.ibb.co/vd8Q9L3/IMG-6207.jpg"
        }
    ]
}
```



------



## 获取群成员列表

描述：根据群聊ID获取群聊的成员信息的列表



#### 请求语法

```http
GET /im/group/members HTTP/1.1
Host: ins.itstudio.club
Authorization: Token 令牌值
```



#### 请求元素

| 名称    | 类型 | 是否必选 | 描述   |
| ------- | ---- | -------- | ------ |
| groupID | Int  | 是       | 群聊ID |



#### 响应元素

| 名称        | 类型   | 描述                                                    |
| ----------- | ------ | ------------------------------------------------------- |
| status      | Int    | 状态码                                                  |
| message     | String | 状态信息                                                |
| data        | Array  | 群成员列表。子节点：id, headPicture, userName, nickName |
| id          | Int    | 用户ID                                                  |
| headPicture | String | 头像URL                                                 |
| userName    | String | 群聊名                                                  |
| nickName    | String | 昵称                                                    |



#### 响应示例

```json
{
    "status": 200,
    "message": "success",
    "data": [
        {
            "id": 2,
            "headPicture": "profile_picture/default.jpg",
            "userName": "陈开拓",
            "nickName": "陈开拓"
        },
        {   
            "id": 3,
            "headPicture": "profile_picture/default.jpg",
            "userName": "Alex",
            "nickName": "Alex"
        }
    ]
}
```



------



## 退出群聊

描述： 创建者退群会自动解散群，普通成员直接退群



#### 请求语法

```http
DELETE /im/group/exit HTTP/1.1
Host: ins.itstudio.club
Authorization: Token 令牌值
```



#### 请求元素

| 名称    | 类型 | 是否必选 | 描述   |
| ------- | ---- | -------- | ------ |
| groupID | Int  | 是       | 群聊ID |



#### 响应元素

| 名称    | 类型   | 描述     |
| ------- | ------ | -------- |
| status  | Int    | 状态码   |
| message | String | 状态信息 |



------



## 获取个人消息列表

描述：根据用户ID及时间戳获取本人与该用户的历史消息列表，每次获取20条，按时间降序排序



#### 请求语法

```http
GET /im/msg/single/list HTTP/1.1
Host: ins.itstudio.club
Authorization: Token 令牌值
```



#### 请求元素

| 名称   | 类型 | 是否必选 | 描述                     |
| ------ | ---- | -------- | ------------------------ |
| userID | Int  | 是       | 用户ID                   |
| time   | Long | 是       | 获取此时间之前的消息记录 |



#### 响应元素

| 名称    | 类型   | 描述                                             |
| ------- | ------ | ------------------------------------------------ |
| status  | Int    | 状态码                                           |
| message | String | 状态信息                                         |
| data    | Array  | 消息列表。子节点：id, type, text, to, from, time |
| id      | Int    | 消息ID                                           |
| type    | Int    | 消息类型，详见消息格式                           |
| text    | String | 内容                                             |
| to      | Int    | 收件人ID                                         |
| from    | Int    | 发件人ID                                         |
| time    | Long   | 发送时间（时间戳）                               |



#### 响应示例

```json
{
    "status": 200,
    "message": "success",
    "data": [
        {
            "id": 2,
            "type": 0,
            "text": "哈哈哈",
                    "to": 1,
            "from": 2,
            "time": 1574844302138,
        },
        {
            "id": 3,
            "type": 0,
            "text": "我是消息",
                    "to": 2,
            "from": 1,
            "time": 1574844374321,
        }
    ]
}
```



------



## 获取群消息列表

描述：根据群ID及时间戳获取群历史消息列表，每次获取20条，按时间降序排序



#### 请求语法

```http
GET /im/msg/group/list HTTP/1.1
Host: ins.itstudio.club
Authorization: Token 令牌值
```



#### 请求元素

| 名称    | 类型 | 是否必选 | 描述                     |
| ------- | ---- | -------- | ------------------------ |
| groupID | Int  | 是       | 群ID                     |
| time    | Long | 是       | 获取此时间之前的消息记录 |



#### 响应元素

| 名称    | 类型   | 描述                                             |
| ------- | ------ | ------------------------------------------------ |
| status  | Int    | 状态码                                           |
| message | String | 状态信息                                         |
| data    | Array  | 消息列表。子节点：id, type, text, to, from, time |
| id      | Int    | 消息ID                                           |
| type    | Int    | 消息类型，详见消息格式                           |
| text    | String | 内容                                             |
| to      | Int    | 群ID                                             |
| from    | Int    | 发件人ID                                         |
| time    | Long   | 发送时间（时间戳）                               |



#### 响应示例

```json
{
    "status": 200,
    "message": "success",
    "data": [
        {
            "id": 4,
            "type": 2,
            "text": "哈哈哈",
                    "to": 6,
            "from": 2,
            "time": 1574844302138,
        },
        {
            "id": 5,
            "type": 2,
            "text": "我是消息",
                    "to": 6,
            "from": 1,
            "time": 1574844374321,
        }
    ]
}
```



------



## 获取未读消息列表

描述：获取用户因离线而未收到的消息列表，仅包含发件人或群的最后一条消息内容以及未收到的消息数量



#### 请求语法

```http
GET /im/unread/list HTTP/1.1
Host: ins.itstudio.club
Authorization: Token 令牌值
```



#### 响应元素

| 名称       | 类型   | 描述                                                         |
| ---------- | ------ | ------------------------------------------------------------ |
| status     | Int    | 状态码                                                       |
| message    | String | 状态信息                                                     |
| data       | 容器   | 子节点：msgList，unreadList                                  |
|            |        |                                                              |
| unreadList | Array  | 未读列表。子节点:id, to, from, messageID, count              |
| id         | Int    | 未读ID                                                       |
| to         | Int    | 收件人ID                                                     |
| from       | Int    | 发件人ID或群ID                                               |
| messageID  | Int    | 消息ID，可以在消息列表中通过此ID获取完整消息                 |
| count      | Int    | 未读数量                                                     |
|            |        |                                                              |
| msgList    | Array  | 消息列表，记录发件人或群聊中的最后一条消息。子节点:id, type, text, to, from, time |
| id         | Int    | 消息ID                                                       |
| type       | Int    | 消息类型                                                     |
| text       | String | 消息内容                                                     |
| to         | Int    | type为0、1时代表收件人ID   2、3时代表群ID                    |
| from       | Int    | 发件人ID                                                     |
| time       | Long   | 发送时间（时间戳）                                           |



#### 响应示例

```json
{
    "status": 200,
    "message": "success",
    "data": {
      "unreadList":[
        {
          "id": 10,
          "to": 2,
          "from": 3,
          "messageID": 4,
          "count": 5
        },
        {
          "id": 11,
          "to": 2,
          "from": 4,
          "messageID": 7,
          "count": 1
        }
      ],
      "msgList":[
        {
            "id": 4,
            "type": 0,
            "text": "我是消息",
                    "to": 2,
            "from": 3,
            "time": 1574844374321,
        },
        {
            "id": 7,
            "type": 0,
            "text": "Hello",
                    "to": 2,
            "from": 4,
            "time": 1574844371011,
        }
      ]
    }
}
```





## 标记消息已读

描述：将上述的未读消息，把未读ID发至服务器，确认为已读，删除未读记录



#### 请求语法

```http
DELETE /im/unread HTTP/1.1
Host: ins.itstudio.club
Authorization: Token 令牌值
```



#### 请求元素

| 名称     | 类型 | 是否必选 | 描述                     |
| -------- | ---- | -------- | ------------------------ |
| unreadID | Int  | 是       | 未读ID，通过上一接口获取 |



#### 响应元素

| 名称    | 类型   | 描述     |
| ------- | ------ | -------- |
| status  | Int    | 状态码   |
| message | String | 状态信息 |



#### 响应示例

```json
{
    "status": 200,
    "message": "success",
}
```





## 图片上传

[API文档](https://api.imgbb.com/)

key: 0181579730be2a5571ac616cd1ab4d54





## 异常处理



| status | message  | 描述                                            |
| ------ | -------- | ----------------------------------------------- |
| 200    | success  | 正常                                            |
| 400    | 异常信息 | 出现异常，导致失败，message根据场景不同内容不同 |
| 401    | 会话失效 | 登录失效，请重新登录                            |
| 405    | 权限错误 |                                                 |