class Content < ActiveRecord::Base
  has_many :tags, :through => :content_tag
  
  scope :alphabetical, order("name")
  scope :for_tag,   lambda { |tag_id| where("tag_id = ?", tag_id) }
  
end
