package go.id.diskominfo.INTERFACE

import go.id.diskominfo.POJO.User

interface UserView {
    fun showDataUser(listUser:List<User>)
}