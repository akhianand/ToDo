
package com.akhilesh.todo

class ToDoDataEntry {
    constructor(ids: Int, headline: String, descriptions: String, deadline: String) {
        this.ids = ids
        this.headline = headline
        this.descriptions = descriptions
        this.deadline = deadline
    }

    var ids: Int;
    var headline: String;
    var descriptions: String;
    var deadline: String;


}