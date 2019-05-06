package go.id.kominfo.INTERFACE

import go.id.kominfo.POJO.User

interface UserView {
    fun showDataUser(listUser:List<User>)
}