package go.id.dinkop.INTERFACE

import go.id.dinkop.POJO.User

interface UserView {
    fun showDataUser(listUser:List<User>)
}