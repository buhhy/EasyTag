class CreateContentTags < ActiveRecord::Migration
  def change
    create_table :content_tags do |t|
      t.int :content_id
      t.int :tag_id

      t.timestamps
    end
  end
end
