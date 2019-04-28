package go.id.kominfo.INTERFACE

import go.id.kominfo.POJO.User

interface UserView {
    fun showData(listUser:List<User>)
}